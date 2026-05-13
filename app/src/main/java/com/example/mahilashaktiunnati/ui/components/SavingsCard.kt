package com.example.mahilashaktiunnati.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mahilashaktiunnati.data.local.entity.SavingsEntity
import com.example.mahilashaktiunnati.util.FinancialUtils

/**
 * Reusable savings card for displaying a savings entry.
 */
@Composable
fun SavingsCard(
    savings: SavingsEntity,
    memberName: String,
    modifier: Modifier = Modifier,
    onMarkAsPaid: (() -> Unit)? = null
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = memberName,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = FinancialUtils.formatDate(savings.paymentDate),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = FinancialUtils.formatCurrencyShort(savings.amountPaid),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                SavingsStatusBadge(status = savings.status)
                
                if (savings.status == com.example.mahilashaktiunnati.data.local.SavingsStatus.PENDING && onMarkAsPaid != null) {
                    TextButton(
                        onClick = onMarkAsPaid,
                        modifier = Modifier.height(30.dp),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp)
                    ) {
                        Text("Mark PAID", style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
        }
    }
}
