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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.mahilashaktiunnati.data.local.LoanStatus
import com.example.mahilashaktiunnati.data.local.entity.MemberEntity
import com.example.mahilashaktiunnati.ui.components.*
import com.example.mahilashaktiunnati.util.FinancialUtils
import com.example.mahilashaktiunnati.viewmodel.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemberProfileScreen(
    navController: NavController, memberId: String?,
    memberViewModel: MemberViewModel, savingsViewModel: SavingsViewModel,
    loanViewModel: LoanViewModel, repaymentViewModel: RepaymentViewModel
) {
    if (memberId == null) { Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("Member not found") }; return }
    val members by memberViewModel.members.collectAsState()
    val member = members.find { it.memberId == memberId }
    val memberSavings by savingsViewModel.getSavingsForMember(memberId).collectAsState(initial = emptyList())
    val memberLoans by loanViewModel.getLoansForMember(memberId).collectAsState(initial = emptyList())
    val totalSavings = memberSavings.filter { it.status.name == "PAID" }.sumOf { it.amountPaid }
    val activeLoans = memberLoans.filter { it.status == LoanStatus.ACTIVE }
    var selectedTab by remember { mutableIntStateOf(0) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    if (member == null) { Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }; return }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Member Profile", fontWeight = FontWeight.Bold) }, navigationIcon = { IconButton(onClick = { navController.popBackStack() }) { Icon(Icons.Filled.ArrowBack, "Back") } }, actions = { IconButton(onClick = { navController.navigate("edit_member/${member.memberId}") }) { Icon(Icons.Filled.Edit, "Edit") }; IconButton(onClick = { showDeleteDialog = true }) { Icon(Icons.Filled.Delete, "Delete") } }, colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary, titleContentColor = MaterialTheme.colorScheme.onPrimary, navigationIconContentColor = MaterialTheme.colorScheme.onPrimary, actionIconContentColor = MaterialTheme.colorScheme.onPrimary)) },
        bottomBar = { Row(Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) { Button(onClick = { navController.navigate("savings_entry?memberId=${member.memberId}") }, Modifier.weight(1f).height(52.dp), shape = RoundedCornerShape(12.dp)) { Text("Add Savings", fontWeight = FontWeight.Bold) }; OutlinedButton(onClick = { navController.navigate("loan_application?memberId=${member.memberId}") }, Modifier.weight(1f).height(52.dp), shape = RoundedCornerShape(12.dp)) { Text("Apply Loan", fontWeight = FontWeight.Bold) } } }
    ) { padding ->
        LazyColumn(Modifier.fillMaxSize().padding(padding).background(MaterialTheme.colorScheme.background), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            item { ProfileHeader(member) }
            item { Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) { MetricCard("Total Savings", FinancialUtils.formatCurrencyShort(totalSavings), "${memberSavings.size} entries", Icons.Filled.Savings, modifier = Modifier.weight(1f), iconTint = MaterialTheme.colorScheme.secondary); MetricCard("Loan Balance", FinancialUtils.formatCurrencyShort(activeLoans.sumOf { it.remainingBalance }), "${activeLoans.size} active", Icons.Filled.AccountBalance, modifier = Modifier.weight(1f), iconTint = MaterialTheme.colorScheme.error) } }
            item { TabRow(selectedTab, containerColor = MaterialTheme.colorScheme.surface) { listOf("Savings", "Loans", "Repayments").forEachIndexed { i, t -> Tab(selectedTab == i, { selectedTab = i }, text = { Text(t, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelMedium) }) } } }
            when (selectedTab) {
                0 -> items(memberSavings) { s -> SavingsCard(s, member.fullName, onMarkAsPaid = { savingsViewModel.updateSavingsStatus(s.savingsId, com.example.mahilashaktiunnati.data.local.SavingsStatus.PAID) }) }
                1 -> items(memberLoans) { l -> LoanCard(l, member.fullName, onRepay = if (l.status == LoanStatus.ACTIVE) {{ navController.navigate("repayment_tracker/${l.loanId}") }} else null) }
                2 -> {
                    items(memberLoans) { loan ->
                        val reps by repaymentViewModel.getRepaymentsForLoan(loan.loanId).collectAsState(initial = emptyList())
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            reps.forEach { r ->
                                Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(1.dp)) { Row(Modifier.fillMaxWidth().padding(14.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) { Column { Text("Loan: ${loan.loanId.take(8)}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant); Text(FinancialUtils.formatDate(r.paymentDate), style = MaterialTheme.typography.bodyMedium) }; Column(horizontalAlignment = Alignment.End) { Text(FinancialUtils.formatCurrencyShort(r.amountPaid), fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary); Text("Bal: ${FinancialUtils.formatCurrencyShort(r.balanceRemaining)}", style = MaterialTheme.typography.bodySmall) } } }
                            }
                        }
                        Spacer(Modifier.height(16.dp))
                    }
                }
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Member", fontWeight = FontWeight.Bold) },
            text = { Text("Are you sure you want to delete ${member.fullName}? This will permanently remove all their savings, loans, and repayments. This action cannot be undone.") },
            confirmButton = {
                TextButton(onClick = {
                    showDeleteDialog = false
                    memberViewModel.deleteMember(member)
                    navController.popBackStack()
                }) {
                    Text("Delete", color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun ProfileHeader(member: MemberEntity) {
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        if (member.photoUri != null) { AsyncImage(member.photoUri, "Photo", contentScale = ContentScale.Crop, modifier = Modifier.size(90.dp).clip(CircleShape)) }
        else { Surface(Modifier.size(90.dp), shape = CircleShape, color = MaterialTheme.colorScheme.primaryContainer) { Box(contentAlignment = Alignment.Center) { Icon(Icons.Filled.Person, null, Modifier.size(48.dp), tint = MaterialTheme.colorScheme.onPrimaryContainer) } } }
        Spacer(Modifier.height(12.dp))
        Text(member.fullName, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Text("ID: ${member.memberId} • Age: ${member.age}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(member.contactNumber, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(member.address, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text("Joined: ${FinancialUtils.formatDate(member.joiningDate)}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
    }
}
