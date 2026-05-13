package com.example.mahilashaktiunnati.data.local.dao

import androidx.room.*
import com.example.mahilashaktiunnati.data.local.LoanStatus
import com.example.mahilashaktiunnati.data.local.entity.LoanEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for Loan operations.
 * Manages loan lifecycle: PENDING_APPROVAL → ACTIVE → CLOSED.
 */
@Dao
interface LoanDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLoan(loan: LoanEntity)

    @Update
    suspend fun updateLoan(loan: LoanEntity)

    @Delete
    suspend fun deleteLoan(loan: LoanEntity)

    @Query("SELECT * FROM loans ORDER BY loanDate DESC")
    fun getAllLoans(): Flow<List<LoanEntity>>

    @Query("SELECT * FROM loans WHERE loanId = :loanId")
    suspend fun getLoanById(loanId: String): LoanEntity?

    @Query("SELECT * FROM loans WHERE loanId = :loanId")
    fun getLoanByIdFlow(loanId: String): Flow<LoanEntity?>

    @Query("SELECT * FROM loans WHERE memberId = :memberId ORDER BY loanDate DESC")
    fun getLoansForMember(memberId: String): Flow<List<LoanEntity>>

    @Query("SELECT * FROM loans WHERE memberId = :memberId AND status = 'ACTIVE'")
    fun getActiveLoansForMember(memberId: String): Flow<List<LoanEntity>>

    @Query("SELECT * FROM loans WHERE status = :status ORDER BY loanDate DESC")
    fun getLoansByStatus(status: LoanStatus): Flow<List<LoanEntity>>

    @Query("SELECT COUNT(*) FROM loans WHERE status = 'ACTIVE'")
    fun getActiveLoanCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM loans WHERE memberId = :memberId AND status = 'ACTIVE'")
    suspend fun hasActiveUnpaidLoan(memberId: String): Int

    @Query("SELECT COALESCE(SUM(remainingBalance), 0.0) FROM loans WHERE status = 'ACTIVE'")
    fun getTotalPendingRepayments(): Flow<Double>

    @Query("SELECT COALESCE(SUM(loanAmount), 0.0) FROM loans")
    fun getTotalLoansGiven(): Flow<Double>

    @Query("SELECT COALESCE(SUM(totalAmountWithInterest - loanAmount), 0.0) FROM loans WHERE status = 'CLOSED'")
    fun getTotalInterestEarned(): Flow<Double>

    @Query("SELECT COUNT(*) FROM loans WHERE status = 'PENDING_APPROVAL'")
    fun getPendingApprovalCount(): Flow<Int>
}
