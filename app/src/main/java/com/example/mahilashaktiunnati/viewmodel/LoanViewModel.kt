package com.example.mahilashaktiunnati.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mahilashaktiunnati.data.local.LoanStatus
import com.example.mahilashaktiunnati.data.local.entity.LoanEntity
import com.example.mahilashaktiunnati.data.repository.LoanRepository
import com.example.mahilashaktiunnati.util.ValidationUtils
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Date

/**
 * ViewModel for Loan management screens.
 * Handles loan application, approval, rejection, and status filtering.
 */
class LoanViewModel(private val loanRepository: LoanRepository) : ViewModel() {

    val allLoans: StateFlow<List<LoanEntity>> = loanRepository.allLoans
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val activeLoanCount: StateFlow<Int> = loanRepository.activeLoanCount
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _successMessage = MutableStateFlow<String?>(null)
    val successMessage: StateFlow<String?> = _successMessage.asStateFlow()

    fun applyForLoan(
        memberId: String,
        loanAmount: Double,
        interestRate: Double,
        loanDate: Date,
        dueDate: Date
    ) {
        if (memberId.isBlank()) {
            _errorMessage.value = "Please select a valid member."
            return
        }
        if (!ValidationUtils.isValidAmount(loanAmount)) {
            _errorMessage.value = "Invalid loan amount. Must be greater than ₹0."
            return
        }
        if (!ValidationUtils.isValidInterestRate(interestRate)) {
            _errorMessage.value = "Invalid interest rate. Must be between 0% and 50%."
            return
        }
        if (!ValidationUtils.isValidDateRange(loanDate, dueDate)) {
            _errorMessage.value = "Due date must be after the loan start date."
            return
        }

        viewModelScope.launch {
            try {
                loanRepository.applyForLoan(memberId, loanAmount, interestRate, loanDate, dueDate)
                _successMessage.value = "Loan application submitted"
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Failed to apply for loan"
            }
        }
    }

    fun approveLoan(loanId: String) {
        viewModelScope.launch {
            try {
                loanRepository.approveLoan(loanId)
                _successMessage.value = "Loan approved successfully"
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Failed to approve loan"
            }
        }
    }

    fun rejectLoan(loanId: String) {
        viewModelScope.launch {
            try {
                loanRepository.rejectLoan(loanId)
                _successMessage.value = "Loan rejected"
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Failed to reject loan"
            }
        }
    }

    fun getLoansForMember(memberId: String): Flow<List<LoanEntity>> {
        return loanRepository.getLoansForMember(memberId)
    }

    fun getLoansByStatus(status: LoanStatus): Flow<List<LoanEntity>> {
        return loanRepository.getLoansByStatus(status)
    }

    fun getLoanByIdFlow(loanId: String): Flow<LoanEntity?> {
        return loanRepository.getLoanByIdFlow(loanId)
    }

    fun clearError() { _errorMessage.value = null }
    fun clearSuccess() { _successMessage.value = null }
}

class LoanViewModelFactory(private val repository: LoanRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoanViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoanViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
