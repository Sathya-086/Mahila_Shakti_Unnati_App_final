package com.example.mahilashaktiunnati.viewmodel

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mahilashaktiunnati.data.local.entity.LoanEntity
import com.example.mahilashaktiunnati.data.local.entity.MemberEntity
import com.example.mahilashaktiunnati.data.local.entity.SavingsEntity
import com.example.mahilashaktiunnati.data.repository.LoanRepository
import com.example.mahilashaktiunnati.data.repository.MemberRepository
import com.example.mahilashaktiunnati.data.repository.SavingsRepository
import com.example.mahilashaktiunnati.data.local.LoanStatus
import com.example.mahilashaktiunnati.util.ReportGenerator
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * ViewModel for Financial Reports screen.
 * Aggregates data from all repositories and generates shareable reports.
 */
class ReportViewModel(
    private val memberRepository: MemberRepository,
    private val savingsRepository: SavingsRepository,
    private val loanRepository: LoanRepository
) : ViewModel() {

    val members: StateFlow<List<MemberEntity>> = memberRepository.allMembers
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allSavings: StateFlow<List<SavingsEntity>> = savingsRepository.allSavings
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allLoans: StateFlow<List<LoanEntity>> = loanRepository.allLoans
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val totalGroupSavings: StateFlow<Double> = savingsRepository.totalGroupSavings
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    val totalLoansGiven: StateFlow<Double> = loanRepository.totalLoansGiven
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    val totalInterestEarned: StateFlow<Double> = loanRepository.totalInterestEarned
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    private val _selectedReportType = MutableStateFlow("Group Summary")
    val selectedReportType: StateFlow<String> = _selectedReportType.asStateFlow()

    private val _selectedMemberId = MutableStateFlow<String?>(null)
    val selectedMemberId: StateFlow<String?> = _selectedMemberId.asStateFlow()

    private val _selectedLoanStatus = MutableStateFlow<LoanStatus?>(null)
    val selectedLoanStatus: StateFlow<LoanStatus?> = _selectedLoanStatus.asStateFlow()

    private val _startDate = MutableStateFlow<String>("")
    val startDate: StateFlow<String> = _startDate.asStateFlow()

    private val _endDate = MutableStateFlow<String>("")
    val endDate: StateFlow<String> = _endDate.asStateFlow()

    fun updateReportType(type: String) { _selectedReportType.value = type }
    fun updateMemberId(id: String?) { _selectedMemberId.value = id }
    fun updateLoanStatus(status: LoanStatus?) { _selectedLoanStatus.value = status }
    fun updateStartDate(date: String) { _startDate.value = date }
    fun updateEndDate(date: String) { _endDate.value = date }

    private fun parseDate(dateString: String): Date? {
        return try {
            if (dateString.isNotBlank()) SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(dateString) else null
        } catch (e: Exception) { null }
    }

    /**
     * Generate and share the group report via WhatsApp or other apps.
     */
    fun shareReport(context: Context) {
        val start = parseDate(_startDate.value)
        val end = parseDate(_endDate.value)

        val filteredSavings = allSavings.value.filter {
            (start == null || !it.paymentDate.before(start)) &&
            (end == null || !it.paymentDate.after(end))
        }

        val filteredLoans = allLoans.value.filter {
            (start == null || !it.loanDate.before(start)) &&
            (end == null || !it.loanDate.after(end)) &&
            (_selectedLoanStatus.value == null || it.status == _selectedLoanStatus.value)
        }

        val reportText = when (_selectedReportType.value) {
            "Member Report" -> {
                val member = members.value.find { it.memberId == _selectedMemberId.value }
                if (member != null) {
                    val memberSavings = filteredSavings.filter { it.memberId == member.memberId }
                    ReportGenerator.generateMemberSavingsReport(
                        member = member,
                        savings = memberSavings,
                        totalSavings = memberSavings.filter { it.status.name == "PAID" }.sumOf { it.amountPaid }
                    )
                } else "Please select a member."
            }
            "Loan Status Report" -> {
                val memberNames = members.value.associate { it.memberId to it.fullName }
                ReportGenerator.generateLoanSummaryReport(filteredLoans, memberNames)
            }
            else -> {
                ReportGenerator.generateGroupReport(
                    members = members.value,
                    savings = filteredSavings,
                    loans = filteredLoans,
                    totalSavings = filteredSavings.filter { it.status.name == "PAID" }.sumOf { it.amountPaid },
                    totalLoansGiven = filteredLoans.sumOf { it.loanAmount },
                    totalInterestEarned = filteredLoans.filter { it.status == LoanStatus.CLOSED }.sumOf { it.totalAmountWithInterest - it.loanAmount }
                )
            }
        }

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            setPackage("com.whatsapp")
            putExtra(Intent.EXTRA_TEXT, reportText)
        }

        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            // Fallback to generic share if WhatsApp is not installed
            val fallbackIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, reportText)
            }
            context.startActivity(Intent.createChooser(fallbackIntent, "Share Report via"))
        }
    }
}

class ReportViewModelFactory(
    private val memberRepository: MemberRepository,
    private val savingsRepository: SavingsRepository,
    private val loanRepository: LoanRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReportViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ReportViewModel(memberRepository, savingsRepository, loanRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
