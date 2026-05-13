package com.example.mahilashaktiunnati.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.mahilashaktiunnati.data.local.LoanStatus
import java.util.Date

/**
 * Represents a loan issued to a SHG member.
 * Tracks loan amount, interest, balance, and lifecycle status.
 *
 * Interest is calculated using Simple Interest formula:
 * SI = P × R × T / 100
 * where P = principal, R = annual rate, T = time in years
 */
@Entity(
    tableName = "loans",
    foreignKeys = [
        ForeignKey(
            entity = MemberEntity::class,
            parentColumns = ["memberId"],
            childColumns = ["memberId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["memberId"])]
)
data class LoanEntity(
    @PrimaryKey
    val loanId: String,
    val memberId: String,
    val loanAmount: Double,
    val interestRate: Double,        // Annual interest rate in percentage
    val loanDate: Date,
    val dueDate: Date,
    val totalAmountWithInterest: Double,
    val remainingBalance: Double,
    val status: LoanStatus = LoanStatus.PENDING_APPROVAL
) {
    /** Repayment progress as a float between 0.0 and 1.0 */
    val repaymentProgress: Float
        get() = if (totalAmountWithInterest > 0) {
            ((totalAmountWithInterest - remainingBalance) / totalAmountWithInterest).toFloat()
                .coerceIn(0f, 1f)
        } else 0f

    /** Amount already repaid */
    val amountRepaid: Double
        get() = totalAmountWithInterest - remainingBalance
}
