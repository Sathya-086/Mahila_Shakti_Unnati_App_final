package com.example.mahilashaktiunnati.data.local.dao

import androidx.room.*
import com.example.mahilashaktiunnati.data.local.entity.RepaymentEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for Repayment operations.
 * Tracks individual installment payments against loans.
 */
@Dao
interface RepaymentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepayment(repayment: RepaymentEntity)

    @Update
    suspend fun updateRepayment(repayment: RepaymentEntity)

    @Delete
    suspend fun deleteRepayment(repayment: RepaymentEntity)

    @Query("SELECT * FROM repayments WHERE loanId = :loanId ORDER BY paymentDate DESC")
    fun getRepaymentsForLoan(loanId: String): Flow<List<RepaymentEntity>>

    @Query("SELECT COALESCE(SUM(amountPaid), 0.0) FROM repayments WHERE loanId = :loanId")
    fun getTotalRepaidForLoan(loanId: String): Flow<Double>

    @Query("SELECT * FROM repayments ORDER BY paymentDate DESC")
    fun getAllRepayments(): Flow<List<RepaymentEntity>>

    @Query("SELECT * FROM repayments WHERE repaymentId = :repaymentId")
    suspend fun getRepaymentById(repaymentId: String): RepaymentEntity?
}
