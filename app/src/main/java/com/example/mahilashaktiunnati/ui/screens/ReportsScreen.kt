package com.example.mahilashaktiunnati.ui.screens

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mahilashaktiunnati.data.local.LoanStatus
import com.example.mahilashaktiunnati.ui.components.MetricCard
import com.example.mahilashaktiunnati.util.FinancialUtils
import com.example.mahilashaktiunnati.viewmodel.ReportViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsScreen(navController: NavController, reportViewModel: ReportViewModel) {
    val context = LocalContext.current
    val members by reportViewModel.members.collectAsState()
    val allSavings by reportViewModel.allSavings.collectAsState()
    val allLoans by reportViewModel.allLoans.collectAsState()
    val totalSavings by reportViewModel.totalGroupSavings.collectAsState()
    val totalLoansGiven by reportViewModel.totalLoansGiven.collectAsState()
    val totalInterestEarned by reportViewModel.totalInterestEarned.collectAsState()
    val activeLoans = allLoans.count { it.status == LoanStatus.ACTIVE }
    val closedLoans = allLoans.count { it.status == LoanStatus.CLOSED }
    val totalRepaid = allLoans.sumOf { it.amountRepaid }

    val reportType by reportViewModel.selectedReportType.collectAsState()
    val memberId by reportViewModel.selectedMemberId.collectAsState()
    val loanStatus by reportViewModel.selectedLoanStatus.collectAsState()
    val startDate by reportViewModel.startDate.collectAsState()
    val endDate by reportViewModel.endDate.collectAsState()

    var expandedReportType by remember { mutableStateOf(false) }
    var expandedMember by remember { mutableStateOf(false) }
    var expandedStatus by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Financial Reports", fontWeight = FontWeight.Bold) }, actions = { IconButton(onClick = { reportViewModel.shareReport(context) }) { Icon(Icons.Filled.Share, "Share", tint = MaterialTheme.colorScheme.onPrimary) } }, colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary, titleContentColor = MaterialTheme.colorScheme.onPrimary)) }
    ) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).background(MaterialTheme.colorScheme.background).verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            
            // Filter Section
            Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Report Filters", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)

                    ExposedDropdownMenuBox(expanded = expandedReportType, onExpandedChange = { expandedReportType = !expandedReportType }) {
                        OutlinedTextField(
                            value = reportType, onValueChange = {}, readOnly = true, label = { Text("Report Type") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedReportType) },
                            modifier = Modifier.fillMaxWidth().menuAnchor(), colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
                        )
                        ExposedDropdownMenu(expanded = expandedReportType, onDismissRequest = { expandedReportType = false }) {
                            listOf("Group Summary", "Member Report", "Loan Status Report").forEach { type ->
                                DropdownMenuItem(text = { Text(type) }, onClick = { reportViewModel.updateReportType(type); expandedReportType = false })
                            }
                        }
                    }

                    if (reportType == "Member Report") {
                        ExposedDropdownMenuBox(expanded = expandedMember, onExpandedChange = { expandedMember = !expandedMember }) {
                            val memberName = members.find { it.memberId == memberId }?.fullName ?: "Select Member"
                            OutlinedTextField(
                                value = memberName, onValueChange = {}, readOnly = true, label = { Text("Member") },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedMember) },
                                modifier = Modifier.fillMaxWidth().menuAnchor(), colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
                            )
                            ExposedDropdownMenu(expanded = expandedMember, onDismissRequest = { expandedMember = false }) {
                                members.forEach { member ->
                                    DropdownMenuItem(text = { Text(member.fullName) }, onClick = { reportViewModel.updateMemberId(member.memberId); expandedMember = false })
                                }
                            }
                        }
                    }

                    if (reportType == "Loan Status Report") {
                        ExposedDropdownMenuBox(expanded = expandedStatus, onExpandedChange = { expandedStatus = !expandedStatus }) {
                            OutlinedTextField(
                                value = loanStatus?.name?.replace("_", " ") ?: "All Statuses", onValueChange = {}, readOnly = true, label = { Text("Loan Status") },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedStatus) },
                                modifier = Modifier.fillMaxWidth().menuAnchor(), colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
                            )
                            ExposedDropdownMenu(expanded = expandedStatus, onDismissRequest = { expandedStatus = false }) {
                                DropdownMenuItem(text = { Text("All Statuses") }, onClick = { reportViewModel.updateLoanStatus(null); expandedStatus = false })
                                LoanStatus.entries.forEach { status ->
                                    DropdownMenuItem(text = { Text(status.name.replace("_", " ")) }, onClick = { reportViewModel.updateLoanStatus(status); expandedStatus = false })
                                }
                            }
                        }
                    }

                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(value = startDate, onValueChange = { reportViewModel.updateStartDate(it) }, label = { Text("Start (dd/MM/yyyy)") }, modifier = Modifier.weight(1f), singleLine = true)
                        OutlinedTextField(value = endDate, onValueChange = { reportViewModel.updateEndDate(it) }, label = { Text("End (dd/MM/yyyy)") }, modifier = Modifier.weight(1f), singleLine = true)
                    }
                }
            }

            Text("Group Financial Summary", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)

            // Metric Cards
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                MetricCard("Total Savings", FinancialUtils.formatCurrencyShort(totalSavings), "${allSavings.size} entries", Icons.Filled.Savings, modifier = Modifier.weight(1f), iconTint = MaterialTheme.colorScheme.secondary)
                MetricCard("Total Loans", FinancialUtils.formatCurrencyShort(totalLoansGiven), "${allLoans.size} loans", Icons.Filled.AccountBalance, modifier = Modifier.weight(1f))
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                MetricCard("Interest Earned", FinancialUtils.formatCurrencyShort(totalInterestEarned), "From closed loans", Icons.Filled.TrendingUp, modifier = Modifier.weight(1f), iconTint = MaterialTheme.colorScheme.secondary)
                MetricCard("Total Repaid", FinancialUtils.formatCurrencyShort(totalRepaid), "$closedLoans completed", Icons.Filled.CheckCircle, modifier = Modifier.weight(1f))
            }

            // Loan Status Summary
            Card(shape = RoundedCornerShape(16.dp), elevation = CardDefaults.cardElevation(2.dp)) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Loan Status Overview", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) { Text("Active Loans"); Text("$activeLoans", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary) }
                    Divider()
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) { Text("Closed Loans"); Text("$closedLoans", fontWeight = FontWeight.Bold) }
                    Divider()
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) { Text("Total Members"); Text("${members.size}", fontWeight = FontWeight.Bold) }
                }
            }

            // WhatsApp Share Button
            Button(onClick = { reportViewModel.shareReport(context) }, modifier = Modifier.fillMaxWidth().height(56.dp), shape = RoundedCornerShape(14.dp), colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)) {
                Icon(Icons.Filled.Share, null); Spacer(Modifier.width(8.dp)); Text("Share via WhatsApp", fontWeight = FontWeight.Bold)
            }
        }
    }
}
