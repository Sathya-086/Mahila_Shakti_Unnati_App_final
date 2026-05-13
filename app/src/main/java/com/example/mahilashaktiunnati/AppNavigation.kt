package com.example.mahilashaktiunnati

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mahilashaktiunnati.ui.screens.*
import com.example.mahilashaktiunnati.viewmodel.*

/**
 * Main navigation composable with bottom navigation bar and all screen routes.
 */
@Composable
fun AppNavigation(app: MahilaShaktiUnnatiApp) {
    val navController = rememberNavController()

    // Create ViewModels with proper factories
    val dashboardViewModel: DashboardViewModel = viewModel(factory = DashboardViewModelFactory(app.memberRepository, app.savingsRepository, app.loanRepository))
    val memberViewModel: MemberViewModel = viewModel(factory = MemberViewModelFactory(app.memberRepository))
    val savingsViewModel: SavingsViewModel = viewModel(factory = SavingsViewModelFactory(app.savingsRepository))
    val loanViewModel: LoanViewModel = viewModel(factory = LoanViewModelFactory(app.loanRepository))
    val repaymentViewModel: RepaymentViewModel = viewModel(factory = RepaymentViewModelFactory(app.repaymentRepository))
    val reportViewModel: ReportViewModel = viewModel(factory = ReportViewModelFactory(app.memberRepository, app.savingsRepository, app.loanRepository))

    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            val mainRoutes = BottomNavItems.map { it.route }
            val showBottomBar = mainRoutes.any { currentDestination?.route == it }

            if (showBottomBar) {
                NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                    BottomNavItems.forEach { screen ->
                        NavigationBarItem(
                            icon = { Icon(screen.icon, contentDescription = screen.title) },
                            label = { Text(screen.title, style = MaterialTheme.typography.labelSmall) },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(navController = navController, startDestination = "splash", modifier = Modifier.padding(innerPadding)) {
            composable("splash") { SplashScreen(navController) }
            composable("login") { LoginScreen(navController) }
            composable("dashboard") { DashboardScreen(navController, dashboardViewModel) }
            composable("members") { MembersScreen(navController, memberViewModel, savingsViewModel, loanViewModel) }
            composable("loans") { LoansScreen(navController, loanViewModel, memberViewModel) }
            composable("reports") { ReportsScreen(navController, reportViewModel) }
            composable("profile_settings") { ProfileSettingsScreen(navController) }
            composable("add_member") { AddEditMemberScreen(navController, memberViewModel) }
            composable("notifications") { NotificationsScreen(navController, loanViewModel, savingsViewModel, memberViewModel) }

            composable("edit_member/{memberId}", arguments = listOf(navArgument("memberId") { type = NavType.StringType })) { backStackEntry ->
                val memberId = backStackEntry.arguments?.getString("memberId") ?: return@composable
                val members by memberViewModel.members.collectAsState()
                val member = members.find { it.memberId == memberId }
                AddEditMemberScreen(navController, memberViewModel, existingMember = member)
            }

            composable("member_profile/{memberId}", arguments = listOf(navArgument("memberId") { type = NavType.StringType })) { backStackEntry ->
                val memberId = backStackEntry.arguments?.getString("memberId")
                MemberProfileScreen(navController, memberId, memberViewModel, savingsViewModel, loanViewModel, repaymentViewModel)
            }

            composable("savings_entry?memberId={memberId}", arguments = listOf(navArgument("memberId") { type = NavType.StringType; defaultValue = "" })) { backStackEntry ->
                val memberId = backStackEntry.arguments?.getString("memberId")?.takeIf { it.isNotBlank() }
                SavingsEntryScreen(navController, memberViewModel, savingsViewModel, preselectedMemberId = memberId)
            }

            composable("loan_application?memberId={memberId}", arguments = listOf(navArgument("memberId") { type = NavType.StringType; defaultValue = "" })) { backStackEntry ->
                val memberId = backStackEntry.arguments?.getString("memberId")?.takeIf { it.isNotBlank() }
                LoanApplicationScreen(navController, loanViewModel, memberViewModel, preselectedMemberId = memberId)
            }

            composable("repayment_tracker/{loanId}", arguments = listOf(navArgument("loanId") { type = NavType.StringType })) { backStackEntry ->
                val loanId = backStackEntry.arguments?.getString("loanId")
                RepaymentTrackerScreen(navController, loanId, loanViewModel, repaymentViewModel)
            }
        }
    }
}
