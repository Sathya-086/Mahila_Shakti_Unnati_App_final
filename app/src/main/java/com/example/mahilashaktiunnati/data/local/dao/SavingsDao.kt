package com.example.mahilashaktiunnati.data.local.dao

import androidx.room.*
import com.example.mahilashaktiunnati.data.local.SavingsStatus
import com.example.mahilashaktiunnati.data.local.entity.SavingsEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for Savings operations.
 * Tracks weekly savings entries, totals, and payment statuses.
 */
@Dao
interface SavingsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSavings(savings: SavingsEntity)

    @Update
    suspend fun updateSavings(savings: SavingsEntity)

    @Delete
    suspend fun deleteSavings(savings: SavingsEntity)

    @Query("SELECT * FROM savings ORDER BY paymentDate DESC")
    fun getAllSavings(): Flow<List<SavingsEntity>>

    @Query("SELECT * FROM savings WHERE savingsId = :savingsId")
    suspend fun getSavingsById(savingsId: String): SavingsEntity?

    @Query("SELECT * FROM savings WHERE memberId = :memberId ORDER BY paymentDate DESC")
    fun getSavingsForMember(memberId: String): Flow<List<SavingsEntity>>

    @Query("SELECT COALESCE(SUM(amountPaid), 0.0) FROM savings WHERE status = 'PAID'")
    fun getTotalGroupSavings(): Flow<Double>

    @Query("SELECT COALESCE(SUM(amountPaid), 0.0) FROM savings WHERE memberId = :memberId AND status = 'PAID'")
    fun getTotalMemberSavings(memberId: String): Flow<Double>

    @Query("SELECT * FROM savings WHERE status = :status ORDER BY paymentDate DESC")
    fun getSavingsByStatus(status: SavingsStatus): Flow<List<SavingsEntity>>

    @Query("SELECT * FROM savings WHERE status = 'PENDING' ORDER BY paymentDate DESC")
    fun getPendingSavings(): Flow<List<SavingsEntity>>

    @Query("SELECT COUNT(*) FROM savings WHERE status = 'PENDING'")
    fun getPendingSavingsCount(): Flow<Int>
}
