package com.example.mahilashaktiunnati.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.mahilashaktiunnati.data.local.SavingsStatus
import java.util.Date

/**
 * Represents a weekly savings entry for a member.
 * Tracks amount paid and payment status (PAID/PENDING).
 */
@Entity(
    tableName = "savings",
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
data class SavingsEntity(
    @PrimaryKey
    val savingsId: String,
    val memberId: String,
    val amountPaid: Double,
    val paymentDate: Date,
    val status: SavingsStatus = SavingsStatus.PAID
)
