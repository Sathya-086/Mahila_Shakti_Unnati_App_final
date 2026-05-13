package com.example.mahilashaktiunnati.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mahilashaktiunnati.ui.components.MetricCard
import com.example.mahilashaktiunnati.util.FinancialUtils
import com.example.mahilashaktiunnati.viewmodel.LoanViewModel
import com.example.mahilashaktiunnati.viewmodel.RepaymentViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepaymentTrackerScreen(
    navController: NavController, loanId: String?,
    loanViewModel: LoanViewModel, repaymentViewModel: RepaymentViewModel
) {
    if (loanId == null) { Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("Loan not found") }; return }
    val loan by loanViewModel.getLoanByIdFlow(loanId).collectAsState(initial = null)
    val repayments by repaymentViewModel.getRepaymentsForLoan(loanId).collectAsState(initial = emptyList())
    var amountText by remember { mutableStateOf("") }
    val errorMessage by repaymentViewModel.errorMessage.collectAsState()
    val successMessage by repaymentViewModel.successMessage.collectAsState()

    LaunchedEffect(successMessage) { if (successMessage != null) { repaymentViewModel.clearSuccess(); amountText = "" } }
    if (errorMessage != null) { AlertDialog(onDismissRequest = { repaymentViewModel.clearError() }, title = { Text("Error") }, text = { Text(errorMessage!!) }, confirmButton = { TextButton(onClick = { repaymentViewModel.clearError() }) { Text("OK") } }) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Repayment Tracker", fontWeight = FontWeight.Bold) }, navigationIcon = { IconButton(onClick = { navController.popBackStack() }) { Icon(Icons.Filled.ArrowBack, "Back") } }, colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary, titleContentColor = MaterialTheme.colorScheme.onPrimary, navigationIconContentColor = MaterialTheme.colorScheme.onPrimary)) }
    ) { padding ->
        if (loan == null) { Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) { CircularProgressIndicator() }; return@Scaffold }
        val l = loan!!

        LazyColumn(Modifier.fillMaxSize().padding(padding).background(MaterialTheme.colorScheme.background), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            // Balance Overview
            item {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    MetricCard("Remaining", FinancialUtils.formatCurrencyShort(l.remainingBalance), "Balance", Icons.Filled.AccountBalance, modifier = Modifier.weight(1f), iconTint = MaterialTheme.colorScheme.error, valueColor = MaterialTheme.colorScheme.error)
                    MetricCard("Total", FinancialUtils.formatCurrencyShort(l.totalAmountWithInterest), "With Interest", Icons.Filled.Payments, modifier = Modifier.weight(1f))
                }
            }

            // Progress
            item {
                Card(shape = RoundedCornerShape(16.dp), elevation = CardDefaults.cardElevation(2.dp)) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Repayment Progress", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(12.dp))
                        LinearProgressIndicator(progress = l.repaymentProgress, modifier = Modifier.fillMaxWidth().height(12.dp), color = MaterialTheme.colorScheme.secondary, trackColor = MaterialTheme.colorScheme.surfaceVariant)
                        Spacer(Modifier.height(8.dp))
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("${(l.repaymentProgress * 100).toInt()}% Complete", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.Bold)
                            Text("${FinancialUtils.formatCurrencyShort(l.amountRepaid)} paid", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }

            // Record Repayment
            item {
                Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f))) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("Record Installment", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                        OutlinedTextField(value = amountText, onValueChange = { amountText = it.filter { c -> c.isDigit() || c == '.' } }, modifier = Modifier.fillMaxWidth(), label = { Text("Installment Amount (₹)") }, leadingIcon = { Icon(Icons.Filled.CurrencyRupee, null) }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal), shape = RoundedCornerShape(14.dp), singleLine = true)
                        Button(onClick = { val amt = amountText.toDoubleOrNull() ?: 0.0; repaymentViewModel.recordRepayment(loanId, amt) }, modifier = Modifier.fillMaxWidth().height(52.dp), shape = RoundedCornerShape(12.dp), enabled = amountText.isNotBlank()) { Icon(Icons.Filled.Payment, null); Spacer(Modifier.width(8.dp)); Text("Record Payment", fontWeight = FontWeight.Bold) }
                    }
                }
            }

            // History
            item { Text("Installment History", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold) }
            if (repayments.isEmpty()) { item { Text("No repayments recorded yet", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodyMedium) } }
            items(repayments) { r ->
                Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(1.dp)) {
                    Row(Modifier.fillMaxWidth().padding(14.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Column { Text(FinancialUtils.formatDate(r.paymentDate), style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium); Text("Balance after: ${FinancialUtils.formatCurrencyShort(r.balanceRemaining)}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant) }
                        Text(FinancialUtils.formatCurrencyShort(r.amountPaid), style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)
                    }
                }
            }
        }
    }
}
