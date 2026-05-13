package com.example.mahilashaktiunnati.data.repository

import com.example.mahilashaktiunnati.data.local.SavingsStatus
import com.example.mahilashaktiunnati.data.local.dao.SavingsDao
import com.example.mahilashaktiunnati.data.local.entity.SavingsEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date
import java.util.UUID

/**
 * Repository for Savings operations.
 * Handles savings entry, status management, and total calculations.
 */
class SavingsRepository(private val savingsDao: SavingsDao) {

    val allSavings: Flow<List<SavingsEntity>> = savingsDao.getAllSavings()
    val totalGroupSavings: Flow<Double> = savingsDao.getTotalGroupSavings()
    val pendingSavings: Flow<List<SavingsEntity>> = savingsDao.getPendingSavings()
    val pendingSavingsCount: Flow<Int> = savingsDao.getPendingSavingsCount()

    suspend fun addSavings(
        memberId: String,
        amount: Double,
        paymentDate: Date = Date(),
        status: SavingsStatus = SavingsStatus.PAID
    ) {
        require(amount > 0) { "Savings amount must be greater than zero" }
        require(memberId.isNotBlank()) { "Member ID cannot be empty" }

        val savings = SavingsEntity(
            savingsId = UUID.randomUUID().toString(),
            memberId = memberId,
            amountPaid = amount,
            paymentDate = paymentDate,
            status = status
        )
        savingsDao.insertSavings(savings)
    }

    suspend fun updateSavingsStatus(savingsId: String, newStatus: SavingsStatus) {
        val savings = savingsDao.getSavingsById(savingsId)
            ?: throw IllegalArgumentException("Savings entry not found")
        savingsDao.updateSavings(savings.copy(status = newStatus))
    }

    fun getSavingsForMember(memberId: String): Flow<List<SavingsEntity>> {
        return savingsDao.getSavingsForMember(memberId)
    }

    fun getTotalMemberSavings(memberId: String): Flow<Double> {
        return savingsDao.getTotalMemberSavings(memberId)
    }

    fun getSavingsByStatus(status: SavingsStatus): Flow<List<SavingsEntity>> {
        return savingsDao.getSavingsByStatus(status)
    }
}
