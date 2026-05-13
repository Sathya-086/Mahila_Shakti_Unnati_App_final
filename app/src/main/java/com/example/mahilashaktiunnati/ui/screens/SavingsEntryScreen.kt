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
import com.example.mahilashaktiunnati.data.local.SavingsStatus
import com.example.mahilashaktiunnati.viewmodel.MemberViewModel
import com.example.mahilashaktiunnati.viewmodel.SavingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavingsEntryScreen(
    navController: NavController, memberViewModel: MemberViewModel,
    savingsViewModel: SavingsViewModel, preselectedMemberId: String? = null
) {
    val members by memberViewModel.members.collectAsState()
    var selectedMemberId by remember { mutableStateOf(preselectedMemberId ?: "") }
    var amount by remember { mutableStateOf("") }
    var status by remember { mutableStateOf(SavingsStatus.PAID) }
    var expanded by remember { mutableStateOf(false) }
    val successMessage by savingsViewModel.successMessage.collectAsState()
    val errorMessage by savingsViewModel.errorMessage.collectAsState()

    LaunchedEffect(successMessage) { if (successMessage != null) { savingsViewModel.clearSuccess(); navController.popBackStack() } }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Add Savings Entry", fontWeight = FontWeight.Bold) }, navigationIcon = { IconButton(onClick = { navController.popBackStack() }) { Icon(Icons.Filled.ArrowBack, "Back") } }, colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary, titleContentColor = MaterialTheme.colorScheme.onPrimary, navigationIconContentColor = MaterialTheme.colorScheme.onPrimary)) }
    ) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).background(MaterialTheme.colorScheme.background).verticalScroll(rememberScrollState()).padding(24.dp), verticalArrangement = Arrangement.spacedBy(20.dp)) {
            if (errorMessage != null) { Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)) { Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) { Icon(Icons.Filled.Error, null, tint = MaterialTheme.colorScheme.error); Spacer(Modifier.width(8.dp)); Text(errorMessage!!, color = MaterialTheme.colorScheme.onErrorContainer) } } }

            // Member Selection
            ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {
                OutlinedTextField(value = members.find { it.memberId == selectedMemberId }?.fullName ?: "", onValueChange = {}, modifier = Modifier.fillMaxWidth().menuAnchor(), readOnly = true, label = { Text("Select Member *") }, leadingIcon = { Icon(Icons.Filled.Person, null) }, trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) }, shape = RoundedCornerShape(14.dp))
                ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    members.forEach { member -> DropdownMenuItem(text = { Text("${member.fullName} (${member.memberId})") }, onClick = { selectedMemberId = member.memberId; expanded = false }) }
                }
            }

            // Amount
            OutlinedTextField(value = amount, onValueChange = { amount = it.filter { c -> c.isDigit() || c == '.' } }, modifier = Modifier.fillMaxWidth(), label = { Text("Amount Paid (₹) *") }, leadingIcon = { Icon(Icons.Filled.CurrencyRupee, null) }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal), shape = RoundedCornerShape(14.dp), singleLine = true)

            // Status Toggle
            Text("Payment Status", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                SavingsStatus.values().forEach { s ->
                    FilterChip(selected = status == s, onClick = { status = s }, label = { Text(s.name) }, leadingIcon = if (status == s) {{ Icon(Icons.Filled.Check, null, Modifier.size(16.dp)) }} else null, shape = RoundedCornerShape(10.dp))
                }
            }

            Spacer(Modifier.height(16.dp))
            Button(onClick = { savingsViewModel.clearError(); val amt = amount.toDoubleOrNull(); if (amt != null && amt > 0 && selectedMemberId.isNotBlank()) { savingsViewModel.addSavings(selectedMemberId, amt, status = status) } }, modifier = Modifier.fillMaxWidth().height(56.dp), shape = RoundedCornerShape(14.dp), enabled = amount.isNotBlank() && selectedMemberId.isNotBlank()) { Text("Save Entry", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold) }
            OutlinedButton(onClick = { navController.popBackStack() }, modifier = Modifier.fillMaxWidth().height(56.dp), shape = RoundedCornerShape(14.dp)) { Text("Cancel") }
        }
    }
}
