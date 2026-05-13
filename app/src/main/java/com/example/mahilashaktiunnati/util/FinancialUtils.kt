package com.example.mahilashaktiunnati.util

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

/**
 * Utility class for financial calculations.
 * Provides simple interest calculation, currency formatting, and date utilities.
 */
object FinancialUtils {

    /**
     * Round a Double value to exactly 2 decimal places.
     */
    fun roundToTwoDecimals(value: Double): Double {
        return kotlin.math.round(value * 100) / 100.0
    }

    /**
     * Calculate Simple Interest.
     * Formula: SI = P × R × T / 100
     *
     * @param principal The principal amount (P)
     * @param annualRate Annual interest rate in percentage (R)
     * @param timeInYears Time period in years (T)
     * @return The simple interest amount
     */
    fun calculateSimpleInterest(
        principal: Double,
        annualRate: Double,
        timeInYears: Double
    ): Double {
        val interest = (principal * annualRate * timeInYears) / 100.0
        return roundToTwoDecimals(interest)
    }

    /**
     * Calculate total amount with interest using dates.
     * Calculates time period from start and end dates.
     *
     * @param principal The principal amount
     * @param annualRate Annual interest rate in percentage
     * @param startDate Loan start date
     * @param endDate Loan due date
     * @return Total amount including interest (P + SI)
     */
    fun calculateTotalWithInterest(
        principal: Double,
        annualRate: Double,
        startDate: Date,
        endDate: Date
    ): Double {
        val diffInMillis = endDate.time - startDate.time
        val days = TimeUnit.MILLISECONDS.toDays(diffInMillis).coerceAtLeast(1)
        val timeInYears = days / 365.0

        val interest = calculateSimpleInterest(principal, annualRate, timeInYears)
        return roundToTwoDecimals(principal + interest)
    }

    /**
     * Format amount as Indian Rupee currency.
     * Example: 45000.0 → "₹45,000.00"
     */
    fun formatCurrency(amount: Double): String {
        val formatter = NumberFormat.getCurrencyInstance(Locale("en", "IN"))
        formatter.minimumFractionDigits = 2
        formatter.maximumFractionDigits = 2
        return formatter.format(amount)
    }

    /**
     * Format amount as short currency without decimals.
     * Example: 45000.0 → "₹45,000"
     */
    fun formatCurrencyShort(amount: Double): String {
        val formatter = NumberFormat.getNumberInstance(Locale("en", "IN"))
        formatter.minimumFractionDigits = 2
        formatter.maximumFractionDigits = 2
        return "₹${formatter.format(amount)}"
    }

    /**
     * Format a date to a readable string.
     * Example: "15 Mar 2024"
     */
    fun formatDate(date: Date): String {
        val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        return formatter.format(date)
    }

    /**
     * Format date for compact display.
     * Example: "15/03/24"
     */
    fun formatDateShort(date: Date): String {
        val formatter = SimpleDateFormat("dd/MM/yy", Locale.getDefault())
        return formatter.format(date)
    }

    /**
     * Calculate repayment progress as a percentage.
     */
    fun calculateProgress(totalAmount: Double, remainingBalance: Double): Float {
        if (totalAmount <= 0) return 0f
        return ((totalAmount - remainingBalance) / totalAmount).toFloat().coerceIn(0f, 1f)
    }
}
