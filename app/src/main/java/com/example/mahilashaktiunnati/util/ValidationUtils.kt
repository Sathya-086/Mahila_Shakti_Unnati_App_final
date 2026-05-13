package com.example.mahilashaktiunnati.util

import java.util.Date

/**
 * Input validation utilities for the application.
 */
object ValidationUtils {

    /**
     * Validate phone number format (Indian 10-digit number).
     */
    fun isValidPhoneNumber(phone: String): Boolean {
        val cleaned = phone.replace(Regex("[\\s-]"), "")
        return cleaned.matches(Regex("^[6-9]\\d{9}$"))
    }

    /**
     * Validate that an amount is positive and within reasonable bounds.
     */
    fun isValidAmount(amount: Double, maxAmount: Double = 1_000_000.0): Boolean {
        return amount > 0 && amount <= maxAmount
    }

    /**
     * Validate that a name is not blank and contains only valid characters.
     */
    fun isValidName(name: String): Boolean {
        return name.isNotBlank() && name.length >= 2 && name.length <= 100
    }

    /**
     * Validate age is within reasonable bounds.
     */
    fun isValidAge(age: Int): Boolean {
        return age in 18..100
    }

    /**
     * Validate interest rate is reasonable.
     */
    fun isValidInterestRate(rate: Double): Boolean {
        return rate in 0.0..50.0
    }

    /**
     * Parse a string to Double, returning null if invalid.
     */
    fun parseAmount(text: String): Double? {
        return text.replace(",", "").toDoubleOrNull()?.takeIf { it > 0 }
    }

    /**
     * Parse a string to Int, returning null if invalid.
     */
    fun parseAge(text: String): Int? {
        return text.toIntOrNull()?.takeIf { it in 1..150 }
    }

    /**
     * Validate that a due date is after the start date.
     */
    fun isValidDateRange(startDate: Date, endDate: Date): Boolean {
        return endDate.after(startDate)
    }
}
