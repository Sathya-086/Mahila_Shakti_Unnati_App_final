package com.example.mahilashaktiunnati.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mahilashaktiunnati.data.repository.LoanRepository
import com.example.mahilashaktiunnati.data.repository.MemberRepository
import com.example.mahilashaktiunnati.data.repository.SavingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.map
import com.example.mahilashaktiunnati.data.local.entity.SavingsEntity

/**
 * ViewModel for the Dashboard screen.
 * Aggregates key financial metrics from all repositories.
 * Updates instantly when database changes occur.
 */
class DashboardViewModel(
    memberRepository: MemberRepository,
    savingsRepository: SavingsRepository,
    loanRepository: LoanRepository
) : ViewModel() {

    /** Total group savings (sum of all PAID savings entries) */
    val totalGroupSavings: StateFlow<Double> = savingsRepository.totalGroupSavings
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    /** Number of currently active loans */
    val activeLoanCount: StateFlow<Int> = loanRepository.activeLoanCount
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    /** Total pending repayment amount across all active loans */
    val totalPendingRepayments: StateFlow<Double> = loanRepository.totalPendingRepayments
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    /** Total number of registered members */
    val totalMembers: StateFlow<Int> = memberRepository.memberCount
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    /** Number of pending loan approvals */
    val pendingApprovals: StateFlow<Int> = loanRepository.pendingApprovalCount
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    /** Number of pending savings entries */
    val pendingSavings: StateFlow<Int> = savingsRepository.pendingSavingsCount
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    /** 3 most recent savings transactions for Dashboard Activities */
    val recentSavings: StateFlow<List<SavingsEntity>> = savingsRepository.allSavings
        .map { list -> list.sortedByDescending { it.paymentDate }.take(3) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}

class DashboardViewModelFactory(
    private val memberRepository: MemberRepository,
    private val savingsRepository: SavingsRepository,
    private val loanRepository: LoanRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DashboardViewModel(memberRepository, savingsRepository, loanRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
