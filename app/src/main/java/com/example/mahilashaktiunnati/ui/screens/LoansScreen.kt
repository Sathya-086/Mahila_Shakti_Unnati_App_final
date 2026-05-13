package com.example.mahilashaktiunnati.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mahilashaktiunnati.data.local.LoanStatus
import com.example.mahilashaktiunnati.ui.components.LoanCard
import com.example.mahilashaktiunnati.viewmodel.LoanViewModel
import com.example.mahilashaktiunnati.viewmodel.MemberViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoansScreen(navController: NavController, loanViewModel: LoanViewModel, memberViewModel: MemberViewModel) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Active", "Closed", "Pending")
    val allLoans by loanViewModel.allLoans.collectAsState()
    val members by memberViewModel.members.collectAsState()
    val errorMessage by loanViewModel.errorMessage.collectAsState()
    val successMessage by loanViewModel.successMessage.collectAsState()

    LaunchedEffect(successMessage) { if (successMessage != null) loanViewModel.clearSuccess() }
    if (errorMessage != null) { AlertDialog(onDismissRequest = { loanViewModel.clearError() }, title = { Text("Error") }, text = { Text(errorMessage!!) }, confirmButton = { TextButton(onClick = { loanViewModel.clearError() }) { Text("OK") } }) }

    val filteredLoans = when (selectedTab) {
        0 -> allLoans.filter { it.status == LoanStatus.ACTIVE }
        1 -> allLoans.filter { it.status == LoanStatus.CLOSED }
        2 -> allLoans.filter { it.status == LoanStatus.PENDING_APPROVAL }
        else -> allLoans
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Loan Management", fontWeight = FontWeight.Bold) }, colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary, titleContentColor = MaterialTheme.colorScheme.onPrimary)) },
        floatingActionButton = { FloatingActionButton(onClick = { navController.navigate("loan_application") }, containerColor = MaterialTheme.colorScheme.primary) { Icon(Icons.Filled.Add, "Apply for Loan") } }
    ) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).background(MaterialTheme.colorScheme.background)) {
            TabRow(selectedTab, containerColor = MaterialTheme.colorScheme.surface, contentColor = MaterialTheme.colorScheme.primary) {
                tabs.forEachIndexed { i, t -> Tab(selectedTab == i, { selectedTab = i }, text = { Text(t, fontWeight = FontWeight.Bold) }) }
            }
            if (filteredLoans.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("No loans found", color = MaterialTheme.colorScheme.onSurfaceVariant) }
            } else {
                LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(filteredLoans) { loan ->
                        val name = members.find { it.memberId == loan.memberId }?.fullName ?: "Unknown"
                        LoanCard(loan, name,
                            onRepay = if (loan.status == LoanStatus.ACTIVE) {{ navController.navigate("repayment_tracker/${loan.loanId}") }} else null,
                            onApprove = if (loan.status == LoanStatus.PENDING_APPROVAL) {{ loanViewModel.approveLoan(loan.loanId) }} else null,
                            onReject = if (loan.status == LoanStatus.PENDING_APPROVAL) {{ loanViewModel.rejectLoan(loan.loanId) }} else null
                        )
                    }
                }
            }
        }
    }
}
