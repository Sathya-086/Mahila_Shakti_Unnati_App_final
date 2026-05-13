package com.example.mahilashaktiunnati.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mahilashaktiunnati.data.local.LoanStatus
import com.example.mahilashaktiunnati.data.local.SavingsStatus
import com.example.mahilashaktiunnati.ui.theme.*

/**
 * Reusable status badge for displaying loan and savings statuses.
 */
@Composable
fun StatusBadge(
    text: String,
    backgroundColor: Color,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        color = backgroundColor,
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
    }
}

/**
 * Status badge for Loan statuses with appropriate colors.
 */
@Composable
fun LoanStatusBadge(status: LoanStatus, modifier: Modifier = Modifier) {
    val (text, bgColor, textColor) = when (status) {
        LoanStatus.ACTIVE -> Triple("ACTIVE", GreenContainer, GreenDark)
        LoanStatus.CLOSED -> Triple("CLOSED", Color(0xFFE0E0E0), Color(0xFF616161))
        LoanStatus.PENDING_APPROVAL -> Triple("PENDING", OrangeContainer, OnOrangeContainer)
    }
    StatusBadge(text = text, backgroundColor = bgColor, textColor = textColor, modifier = modifier)
}

/**
 * Status badge for Savings statuses with appropriate colors.
 */
@Composable
fun SavingsStatusBadge(status: SavingsStatus, modifier: Modifier = Modifier) {
    val (text, bgColor, textColor) = when (status) {
        SavingsStatus.PAID -> Triple("PAID", GreenContainer, GreenDark)
        SavingsStatus.PENDING -> Triple("PENDING", OrangeContainer, OnOrangeContainer)
    }
    StatusBadge(text = text, backgroundColor = bgColor, textColor = textColor, modifier = modifier)
}
