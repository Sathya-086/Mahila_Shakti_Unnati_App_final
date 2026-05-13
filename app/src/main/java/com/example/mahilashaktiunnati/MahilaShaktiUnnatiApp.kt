package com.example.mahilashaktiunnati

import android.app.Application
import com.example.mahilashaktiunnati.data.local.AppDatabase
import com.example.mahilashaktiunnati.data.repository.LoanRepository
import com.example.mahilashaktiunnati.data.repository.MemberRepository
import com.example.mahilashaktiunnati.data.repository.RepaymentRepository
import com.example.mahilashaktiunnati.data.repository.SavingsRepository

/**
 * Application class — initializes database and all repositories.
 * Repositories are created lazily to avoid blocking app startup.
 */
class MahilaShaktiUnnatiApp : Application() {

    val database by lazy { AppDatabase.getDatabase(this) }

    val memberRepository by lazy { MemberRepository(database.memberDao()) }
    val savingsRepository by lazy { SavingsRepository(database.savingsDao()) }
    val loanRepository by lazy { LoanRepository(database.loanDao()) }
    val repaymentRepository by lazy { RepaymentRepository(database.repaymentDao(), loanRepository) }
}
