package com.example.mahilashaktiunnati.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import com.example.mahilashaktiunnati.viewmodel.LoanViewModel
import com.example.mahilashaktiunnati.viewmodel.MemberViewModel
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoanApplicationScreen(
    navController: NavController, loanViewModel: LoanViewModel,
    memberViewModel: MemberViewModel, preselectedMemberId: String? = null
) {
    val members by memberViewModel.members.collectAsState()
    var selectedMemberId by remember { mutableStateOf(preselectedMemberId ?: "") }
    var loanAmount by remember { mutableStateOf("") }
    var interestRate by remember { mutableStateOf("12") }
    var durationMonths by remember { mutableStateOf("12") }
    var expanded by remember { mutableStateOf(false) }
    val errorMessage by loanViewModel.errorMessage.collectAsState()
    val successMessage by loanViewModel.successMessage.collectAsState()

    LaunchedEffect(successMessage) { if (successMessage != null) { loanViewModel.clearSuccess(); navController.popBackStack() } }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Loan Application", fontWeight = FontWeight.Bold) }, navigationIcon = { IconButton(onClick = { navController.popBackStack() }) { Icon(Icons.Filled.ArrowBack, "Back") } }, colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary, titleContentColor = MaterialTheme.colorScheme.onPrimary, navigationIconContentColor = MaterialTheme.colorScheme.onPrimary)) }
    ) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).background(MaterialTheme.colorScheme.background).verticalScroll(rememberScrollState()).padding(24.dp), verticalArrangement = Arrangement.spacedBy(20.dp)) {
            if (errorMessage != null) { Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)) { Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) { Icon(Icons.Filled.Error, null, tint = MaterialTheme.colorScheme.error); Spacer(Modifier.width(8.dp)); Text(errorMessage!!, color = MaterialTheme.colorScheme.onErrorContainer, style = MaterialTheme.typography.bodySmall) } } }

            Text("Apply for a New Loan", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)

            ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {
                OutlinedTextField(value = members.find { it.memberId == selectedMemberId }?.fullName ?: "", onValueChange = {}, modifier = Modifier.fillMaxWidth().menuAnchor(), readOnly = true, label = { Text("Select Member *") }, leadingIcon = { Icon(Icons.Filled.Person, null) }, trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) }, shape = RoundedCornerShape(14.dp))
                ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    members.forEach { m -> DropdownMenuItem(text = { Text("${m.fullName} (${m.memberId})") }, onClick = { selectedMemberId = m.memberId; expanded = false }) }
                }
            }

            OutlinedTextField(value = loanAmount, onValueChange = { loanAmount = it.filter { c -> c.isDigit() || c == '.' } }, modifier = Modifier.fillMaxWidth(), label = { Text("Loan Amount (₹) *") }, leadingIcon = { Icon(Icons.Filled.CurrencyRupee, null) }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal), shape = RoundedCornerShape(14.dp), singleLine = true)
            OutlinedTextField(value = interestRate, onValueChange = { interestRate = it.filter { c -> c.isDigit() || c == '.' } }, modifier = Modifier.fillMaxWidth(), label = { Text("Interest Rate (% per year) *") }, leadingIcon = { Icon(Icons.Filled.Percent, null) }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal), shape = RoundedCornerShape(14.dp), singleLine = true)
            OutlinedTextField(value = durationMonths, onValueChange = { durationMonths = it.filter { c -> c.isDigit() } }, modifier = Modifier.fillMaxWidth(), label = { Text("Duration (months) *") }, leadingIcon = { Icon(Icons.Filled.CalendarMonth, null) }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), shape = RoundedCornerShape(14.dp), singleLine = true)

            // Preview
            val amt = loanAmount.toDoubleOrNull() ?: 0.0
            val rate = interestRate.toDoubleOrNull() ?: 0.0
            val months = durationMonths.toIntOrNull() ?: 0
            if (amt > 0 && rate > 0 && months > 0) {
                val si = amt * rate * (months / 12.0) / 100.0
                Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer), shape = RoundedCornerShape(14.dp)) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Loan Preview", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) { Text("Principal"); Text(com.example.mahilashaktiunnati.util.FinancialUtils.formatCurrencyShort(amt), fontWeight = FontWeight.Bold) }
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) { Text("Interest (SI)"); Text(com.example.mahilashaktiunnati.util.FinancialUtils.formatCurrencyShort(si), fontWeight = FontWeight.Bold) }
                        Divider()
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) { Text("Total Repayable", fontWeight = FontWeight.Bold); Text(com.example.mahilashaktiunnati.util.FinancialUtils.formatCurrencyShort(amt + si), fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary) }
                    }
                }
            }

            Spacer(Modifier.height(8.dp))
            Button(onClick = {
                loanViewModel.clearError()
                val a = loanAmount.toDoubleOrNull(); val r = interestRate.toDoubleOrNull(); val m = durationMonths.toIntOrNull()
                if (a != null && r != null && m != null && selectedMemberId.isNotBlank()) {
                    val cal = Calendar.getInstance(); val loanDate = cal.time; cal.add(Calendar.MONTH, m); val dueDate = cal.time
                    loanViewModel.applyForLoan(selectedMemberId, a, r, loanDate, dueDate)
                }
            }, Modifier.fillMaxWidth().height(56.dp), shape = RoundedCornerShape(14.dp), enabled = loanAmount.isNotBlank() && interestRate.isNotBlank() && durationMonths.isNotBlank() && selectedMemberId.isNotBlank()) {
                Text("Submit Loan Application", fontWeight = FontWeight.Bold)
            }
        }
    }
}
