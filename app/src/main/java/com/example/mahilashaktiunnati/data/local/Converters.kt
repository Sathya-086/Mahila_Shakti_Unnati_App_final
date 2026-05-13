package com.example.mahilashaktiunnati.data.local

import androidx.room.TypeConverter
import java.util.Date

/**
 * Room TypeConverters for custom types.
 * Converts Date ↔ Long and Enum ↔ String for Room database storage.
 */
class Converters {

    // ── Date Converters ──────────────────────────────────────────

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    // ── SavingsStatus Converters ─────────────────────────────────

    @TypeConverter
    fun fromSavingsStatus(status: SavingsStatus): String {
        return status.name
    }

    @TypeConverter
    fun toSavingsStatus(value: String): SavingsStatus {
        return SavingsStatus.valueOf(value)
    }

    // ── LoanStatus Converters ────────────────────────────────────

    @TypeConverter
    fun fromLoanStatus(status: LoanStatus): String {
        return status.name
    }

    @TypeConverter
    fun toLoanStatus(value: String): LoanStatus {
        return LoanStatus.valueOf(value)
    }
}
