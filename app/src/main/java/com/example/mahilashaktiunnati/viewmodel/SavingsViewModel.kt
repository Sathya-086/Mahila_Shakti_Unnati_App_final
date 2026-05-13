package com.example.mahilashaktiunnati.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mahilashaktiunnati.data.local.SavingsStatus
import com.example.mahilashaktiunnati.data.local.entity.SavingsEntity
import com.example.mahilashaktiunnati.data.repository.SavingsRepository
import com.example.mahilashaktiunnati.util.ValidationUtils
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Date

/**
 * ViewModel for Savings management screens.
 * Handles savings entry, status toggle, and history.
 */
class SavingsViewModel(private val savingsRepository: SavingsRepository) : ViewModel() {

    val allSavings: StateFlow<List<SavingsEntity>> = savingsRepository.allSavings
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val totalGroupSavings: StateFlow<Double> = savingsRepository.totalGroupSavings
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    val pendingSavings: StateFlow<List<SavingsEntity>> = savingsRepository.pendingSavings
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _successMessage = MutableStateFlow<String?>(null)
    val successMessage: StateFlow<String?> = _successMessage.asStateFlow()

    fun addSavings(
        memberId: String,
        amount: Double,
        paymentDate: Date = Date(),
        status: SavingsStatus = SavingsStatus.PAID
    ) {
        if (memberId.isBlank()) {
            _errorMessage.value = "Please select a valid member."
            return
        }
        if (!ValidationUtils.isValidAmount(amount)) {
            _errorMessage.value = "Invalid amount. Savings must be greater than ₹0."
            return
        }

        viewModelScope.launch {
            try {
                savingsRepository.addSavings(memberId, amount, paymentDate, status)
                _successMessage.value = "Savings recorded successfully"
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Failed to record savings"
            }
        }
    }

    fun updateSavingsStatus(savingsId: String, newStatus: SavingsStatus) {
        viewModelScope.launch {
            try {
                savingsRepository.updateSavingsStatus(savingsId, newStatus)
                _successMessage.value = "Status updated"
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Failed to update status"
            }
        }
    }

    fun getSavingsForMember(memberId: String): Flow<List<SavingsEntity>> {
        return savingsRepository.getSavingsForMember(memberId)
    }

    fun getTotalMemberSavings(memberId: String): Flow<Double> {
        return savingsRepository.getTotalMemberSavings(memberId)
    }

    fun clearError() { _errorMessage.value = null }
    fun clearSuccess() { _successMessage.value = null }
}

class SavingsViewModelFactory(private val repository: SavingsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SavingsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SavingsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
