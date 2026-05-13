package com.example.mahilashaktiunnati.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Represents a single repayment installment against a loan.
 * Each repayment reduces the loan's remaining balance.
 * When balance reaches zero, the loan is auto-closed.
 */
@Entity(
    tableName = "repayments",
    foreignKeys = [
        ForeignKey(
            entity = LoanEntity::class,
            parentColumns = ["loanId"],
            childColumns = ["loanId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["loanId"])]
)
data class RepaymentEntity(
    @PrimaryKey
    val repaymentId: String,
    val loanId: String,
    val amountPaid: Double,
    val paymentDate: Date,
    val balanceRemaining: Double
)
