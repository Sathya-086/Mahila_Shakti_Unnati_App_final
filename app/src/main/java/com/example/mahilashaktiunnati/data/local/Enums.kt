package com.example.mahilashaktiunnati.data.local

/**
 * Status for savings entries - tracks whether a weekly savings payment has been made.
 */
enum class SavingsStatus {
    PAID,
    PENDING
}

/**
 * Status for loan lifecycle management.
 * PENDING_APPROVAL → ACTIVE → CLOSED
 */
enum class LoanStatus {
    ACTIVE,
    CLOSED,
    PENDING_APPROVAL
}
