package com.example.mahilashaktiunnati.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mahilashaktiunnati.data.local.entity.RepaymentEntity
import com.example.mahilashaktiunnati.data.repository.RepaymentRepository
import com.example.mahilashaktiunnati.util.ValidationUtils
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Date

/**
 * ViewModel for Repayment tracking.
 * Handles recording repayments and viewing installment history.
 */
class RepaymentViewModel(private val repaymentRepository: RepaymentRepository) : ViewModel() {

    val allRepayments: StateFlow<List<RepaymentEntity>> = repaymentRepository.allRepayments
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _successMessage = MutableStateFlow<String?>(null)
    val successMessage: StateFlow<String?> = _successMessage.asStateFlow()

    fun recordRepayment(
        loanId: String,
        amountPaid: Double,
        paymentDate: Date = Date()
    ) {
        if (loanId.isBlank()) {
            _errorMessage.value = "Please select a valid loan."
            return
        }
        if (amountPaid <= 0) {
            _errorMessage.value = "Invalid repayment amount. Must be greater than ₹0."
            return
        }

        viewModelScope.launch {
            try {
                repaymentRepository.recordRepayment(loanId, amountPaid, paymentDate)
                _successMessage.value = "Repayment recorded successfully"
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Failed to record repayment"
            }
        }
    }

    fun getRepaymentsForLoan(loanId: String): Flow<List<RepaymentEntity>> {
        return repaymentRepository.getRepaymentsForLoan(loanId)
    }

    fun getTotalRepaidForLoan(loanId: String): Flow<Double> {
        return repaymentRepository.getTotalRepaidForLoan(loanId)
    }

    fun clearError() { _errorMessage.value = null }
    fun clearSuccess() { _successMessage.value = null }
}

class RepaymentViewModelFactory(private val repository: RepaymentRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RepaymentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RepaymentViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
