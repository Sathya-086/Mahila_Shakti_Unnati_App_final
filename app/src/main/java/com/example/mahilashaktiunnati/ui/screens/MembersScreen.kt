package com.example.mahilashaktiunnati.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mahilashaktiunnati.ui.components.MemberCard
import com.example.mahilashaktiunnati.viewmodel.MemberViewModel
import com.example.mahilashaktiunnati.viewmodel.SavingsViewModel
import com.example.mahilashaktiunnati.viewmodel.LoanViewModel
import com.example.mahilashaktiunnati.data.local.LoanStatus

/**
 * Members List Screen — Search, member cards, FAB to add.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MembersScreen(
    navController: NavController,
    memberViewModel: MemberViewModel,
    savingsViewModel: SavingsViewModel,
    loanViewModel: LoanViewModel
) {
    val searchQuery by memberViewModel.searchQuery.collectAsState()
    val members by memberViewModel.filteredMembers.collectAsState()
    val allSavings by savingsViewModel.allSavings.collectAsState()
    val allLoans by loanViewModel.allLoans.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Members Directory", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("add_member") },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add New Member")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { memberViewModel.updateSearchQuery(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Search by name or ID") },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
                shape = RoundedCornerShape(14.dp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline
                )
            )

            // Members List
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(members) { member ->
                    val memberSavings = allSavings
                        .filter { it.memberId == member.memberId && it.status.name == "PAID" }
                        .sumOf { it.amountPaid }
                    val hasActiveLoan = allLoans.any { it.memberId == member.memberId && it.status == LoanStatus.ACTIVE }

                    MemberCard(
                        member = member,
                        totalSavings = memberSavings,
                        hasActiveLoan = hasActiveLoan,
                        onClick = {
                            navController.navigate("member_profile/${member.memberId}")
                        }
                    )
                }
            }
        }
    }
}
