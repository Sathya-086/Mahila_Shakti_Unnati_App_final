package com.example.mahilashaktiunnati.data.repository

import com.example.mahilashaktiunnati.data.local.dao.RepaymentDao
import com.example.mahilashaktiunnati.data.local.entity.RepaymentEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date
import java.util.UUID

/**
 * Repository for Repayment operations.
 * Records installments and coordinates with LoanRepository to update loan balance.
 */
class RepaymentRepository(
    private val repaymentDao: RepaymentDao,
    private val loanRepository: LoanRepository
) {

    val allRepayments: Flow<List<RepaymentEntity>> = repaymentDao.getAllRepayments()

    /**
     * Record a repayment installment.
     * Updates the loan's remaining balance and auto-closes if fully repaid.
     */
    suspend fun recordRepayment(
        loanId: String,
        amountPaid: Double,
        paymentDate: Date = Date()
    ) {
        require(amountPaid > 0) { "Repayment amount must be greater than zero" }
        require(loanId.isNotBlank()) { "Loan ID cannot be empty" }

        val loan = loanRepository.getLoanById(loanId)
            ?: throw IllegalArgumentException("Loan not found")

        if (amountPaid > loan.remainingBalance) {
            throw IllegalArgumentException("Repayment amount exceeds remaining balance of ${com.example.mahilashaktiunnati.util.FinancialUtils.formatCurrencyShort(loan.remainingBalance)}")
        }

        val newBalance = com.example.mahilashaktiunnati.util.FinancialUtils.roundToTwoDecimals((loan.remainingBalance - amountPaid).coerceAtLeast(0.0))

        // Create repayment record
        val repayment = RepaymentEntity(
            repaymentId = UUID.randomUUID().toString(),
            loanId = loanId,
            amountPaid = amountPaid,
            paymentDate = paymentDate,
            balanceRemaining = newBalance
        )
        repaymentDao.insertRepayment(repayment)

        // Update loan balance (auto-closes if balance reaches zero)
        loanRepository.updateLoanBalance(loanId, amountPaid)
    }

    fun getRepaymentsForLoan(loanId: String): Flow<List<RepaymentEntity>> {
        return repaymentDao.getRepaymentsForLoan(loanId)
    }

    fun getTotalRepaidForLoan(loanId: String): Flow<Double> {
        return repaymentDao.getTotalRepaidForLoan(loanId)
    }
}
