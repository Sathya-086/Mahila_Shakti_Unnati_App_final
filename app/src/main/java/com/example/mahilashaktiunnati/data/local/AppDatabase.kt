package com.example.mahilashaktiunnati.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.mahilashaktiunnati.data.local.dao.LoanDao
import com.example.mahilashaktiunnati.data.local.dao.MemberDao
import com.example.mahilashaktiunnati.data.local.dao.RepaymentDao
import com.example.mahilashaktiunnati.data.local.dao.SavingsDao
import com.example.mahilashaktiunnati.data.local.entity.LoanEntity
import com.example.mahilashaktiunnati.data.local.entity.MemberEntity
import com.example.mahilashaktiunnati.data.local.entity.RepaymentEntity
import com.example.mahilashaktiunnati.data.local.entity.SavingsEntity

/**
 * Room database for Mahila-Shakti Unnati.
 * Contains tables: members, savings, loans, repayments.
 * Uses TypeConverters for Date and Enum types.
 *
 * Migration Strategy:
 * - Each schema change requires a new Migration object (e.g., MIGRATION_1_2).
 * - Migrations preserve all existing user data.
 * - Destructive fallback is avoided to protect financial records.
 * - exportSchema is enabled so Room generates JSON schema files for
 *   automated migration verification during development.
 */
@Database(
    entities = [
        MemberEntity::class,
        SavingsEntity::class,
        LoanEntity::class,
        RepaymentEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun memberDao(): MemberDao
    abstract fun savingsDao(): SavingsDao
    abstract fun loanDao(): LoanDao
    abstract fun repaymentDao(): RepaymentDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        /**
         * Future migrations go here. Example:
         *
         * val MIGRATION_1_2 = object : Migration(1, 2) {
         *     override fun migrate(db: SupportSQLiteDatabase) {
         *         db.execSQL("ALTER TABLE members ADD COLUMN groupId TEXT NOT NULL DEFAULT ''")
         *     }
         * }
         *
         * Then add .addMigrations(MIGRATION_1_2) to the builder below.
         */

        fun getDatabase(context: Context): AppDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "mahila_shakti_unnati_db"
                )
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
