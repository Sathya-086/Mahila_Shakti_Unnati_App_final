package com.example.mahilashaktiunnati.data.repository

import com.example.mahilashaktiunnati.data.local.LoanStatus
import com.example.mahilashaktiunnati.data.local.dao.LoanDao
import com.example.mahilashaktiunnati.data.local.entity.LoanEntity
import com.example.mahilashaktiunnati.util.FinancialUtils
import kotlinx.coroutines.flow.Flow
import java.util.Date
import java.util.UUID

/**
 * Repository for Loan operations.
 * Handles loan lifecycle: application → approval → active → closed.
 * Enforces business rules like preventing duplicate active loans.
 */
class LoanRepository(private val loanDao: LoanDao) {

    val allLoans: Flow<List<LoanEntity>> = loanDao.getAllLoans()
    val activeLoanCount: Flow<Int> = loanDao.getActiveLoanCount()
    val totalPendingRepayments: Flow<Double> = loanDao.getTotalPendingRepayments()
    val totalLoansGiven: Flow<Double> = loanDao.getTotalLoansGiven()
    val totalInterestEarned: Flow<Double> = loanDao.getTotalInterestEarned()
    val pendingApprovalCount: Flow<Int> = loanDao.getPendingApprovalCount()

    /**
     * Apply for a new loan. Creates loan in PENDING_APPROVAL status.
     * Prevents duplicate active loans for the same member.
     *
     * @throws IllegalStateException if member already has an active unpaid loan
     */
    suspend fun applyForLoan(
        memberId: String,
        loanAmount: Double,
        interestRate: Double,
        loanDate: Date,
        dueDate: Date
    ) {
        require(loanAmount > 0) { "Loan amount must be greater than zero" }
        require(interestRate >= 0) { "Interest rate cannot be negative" }
        require(memberId.isNotBlank()) { "Member ID cannot be empty" }

        // Business Rule: Prevent duplicate active loans
        val activeCount = loanDao.hasActiveUnpaidLoan(memberId)
        if (activeCount > 0) {
            throw IllegalStateException("Member already has an active unpaid loan. Cannot approve a new loan.")
        }

        // Calculate simple interest: SI = P × R × T / 100
        val totalWithInterest = FinancialUtils.calculateTotalWithInterest(
            principal = loanAmount,
            annualRate = interestRate,
            startDate = loanDate,
            endDate = dueDate
        )

        val loan = LoanEntity(
            loanId = UUID.randomUUID().toString(),
            memberId = memberId,
            loanAmount = loanAmount,
            interestRate = interestRate,
            loanDate = loanDate,
            dueDate = dueDate,
            totalAmountWithInterest = totalWithInterest,
            remainingBalance = totalWithInterest,
            status = LoanStatus.PENDING_APPROVAL
        )
        loanDao.insertLoan(loan)
    }

    /**
     * Approve a pending loan application. Changes status to ACTIVE.
     */
    suspend fun approveLoan(loanId: String) {
        val loan = loanDao.getLoanById(loanId)
            ?: throw IllegalArgumentException("Loan not found")

        if (loan.status != LoanStatus.PENDING_APPROVAL) {
            throw IllegalStateException("Only pending loans can be approved")
        }

        // Check again for active loan (in case one was approved between apply and approve)
        val activeCount = loanDao.hasActiveUnpaidLoan(loan.memberId)
        if (activeCount > 0) {
            throw IllegalStateException("Member already has an active unpaid loan.")
        }

        loanDao.updateLoan(loan.copy(status = LoanStatus.ACTIVE))
    }

    /**
     * Reject a pending loan application. Deletes the loan record.
     */
    suspend fun rejectLoan(loanId: String) {
        val loan = loanDao.getLoanById(loanId)
            ?: throw IllegalArgumentException("Loan not found")

        if (loan.status != LoanStatus.PENDING_APPROVAL) {
            throw IllegalStateException("Only pending loans can be rejected")
        }

        loanDao.deleteLoan(loan)
    }

    /**
     * Update loan balance after a repayment.
     * Auto-closes loan when balance reaches zero.
     */
    suspend fun updateLoanBalance(loanId: String, repaymentAmount: Double) {
        val loan = loanDao.getLoanById(loanId)
            ?: throw IllegalArgumentException("Loan not found")

        val newBalance = com.example.mahilashaktiunnati.util.FinancialUtils.roundToTwoDecimals((loan.remainingBalance - repaymentAmount).coerceAtLeast(0.0))
        val newStatus = if (newBalance <= 0.0) LoanStatus.CLOSED else loan.status

        loanDao.updateLoan(
            loan.copy(
                remainingBalance = newBalance,
                status = newStatus
            )
        )
    }

    suspend fun getLoanById(loanId: String): LoanEntity? {
        return loanDao.getLoanById(loanId)
    }

    fun getLoanByIdFlow(loanId: String): Flow<LoanEntity?> {
        return loanDao.getLoanByIdFlow(loanId)
    }

    fun getLoansForMember(memberId: String): Flow<List<LoanEntity>> {
        return loanDao.getLoansForMember(memberId)
    }

    fun getActiveLoansForMember(memberId: String): Flow<List<LoanEntity>> {
        return loanDao.getActiveLoansForMember(memberId)
    }

    fun getLoansByStatus(status: LoanStatus): Flow<List<LoanEntity>> {
        return loanDao.getLoansByStatus(status)
    }
}
