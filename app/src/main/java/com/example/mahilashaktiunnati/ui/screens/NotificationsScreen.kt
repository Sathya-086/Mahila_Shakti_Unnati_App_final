package com.example.mahilashaktiunnati.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mahilashaktiunnati.data.local.LoanStatus
import com.example.mahilashaktiunnati.util.FinancialUtils
import com.example.mahilashaktiunnati.viewmodel.LoanViewModel
import com.example.mahilashaktiunnati.viewmodel.MemberViewModel
import com.example.mahilashaktiunnati.viewmodel.SavingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(
    navController: NavController, loanViewModel: LoanViewModel,
    savingsViewModel: SavingsViewModel, memberViewModel: MemberViewModel
) {
    val allLoans by loanViewModel.allLoans.collectAsState()
    val pendingSavings by savingsViewModel.pendingSavings.collectAsState()
    val members by memberViewModel.members.collectAsState()
    val activeLoans = allLoans.filter { it.status == LoanStatus.ACTIVE }

    data class Alert(val icon: androidx.compose.ui.graphics.vector.ImageVector, val title: String, val subtitle: String, val type: String)
    val alerts = mutableListOf<Alert>()
    activeLoans.forEach { loan ->
        val name = members.find { it.memberId == loan.memberId }?.fullName ?: "Unknown"
        alerts.add(Alert(Icons.Filled.Warning, "Loan Repayment Due", "$name - ${FinancialUtils.formatCurrencyShort(loan.remainingBalance)} remaining", "warning"))
    }
    pendingSavings.forEach { s ->
        val name = members.find { it.memberId == s.memberId }?.fullName ?: "Unknown"
        alerts.add(Alert(Icons.Filled.Savings, "Savings Pending", "$name - ${FinancialUtils.formatCurrencyShort(s.amountPaid)}", "info"))
    }
    allLoans.filter { it.status == LoanStatus.PENDING_APPROVAL }.forEach { loan ->
        val name = members.find { it.memberId == loan.memberId }?.fullName ?: "Unknown"
        alerts.add(Alert(Icons.Filled.Approval, "Loan Pending Approval", "$name - ${FinancialUtils.formatCurrencyShort(loan.loanAmount)}", "pending"))
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Notifications", fontWeight = FontWeight.Bold) }, navigationIcon = { IconButton(onClick = { navController.popBackStack() }) { Icon(Icons.Filled.ArrowBack, "Back") } }, colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary, titleContentColor = MaterialTheme.colorScheme.onPrimary, navigationIconContentColor = MaterialTheme.colorScheme.onPrimary)) }
    ) { padding ->
        if (alerts.isEmpty()) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Filled.NotificationsNone, null, Modifier.size(64.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(Modifier.height(16.dp))
                    Text("No notifications", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("All caught up!", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        } else {
            LazyColumn(Modifier.fillMaxSize().padding(padding).background(MaterialTheme.colorScheme.background), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                item { Text("${alerts.size} Alerts", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onSurfaceVariant) }
                items(alerts) { alert ->
                    val (bgColor, iconColor) = when (alert.type) { "warning" -> MaterialTheme.colorScheme.tertiaryContainer to MaterialTheme.colorScheme.tertiary; "pending" -> MaterialTheme.colorScheme.primaryContainer to MaterialTheme.colorScheme.primary; else -> MaterialTheme.colorScheme.secondaryContainer to MaterialTheme.colorScheme.secondary }
                    Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(14.dp), elevation = CardDefaults.cardElevation(1.dp)) {
                        Row(Modifier.fillMaxWidth().padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                            Surface(modifier = Modifier.size(44.dp), shape = CircleShape, color = bgColor) { Box(contentAlignment = Alignment.Center) { Icon(alert.icon, null, tint = iconColor) } }
                            Spacer(Modifier.width(14.dp))
                            Column(Modifier.weight(1f)) { Text(alert.title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold); Text(alert.subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant) }
                        }
                    }
                }
            }
        }
    }
}
