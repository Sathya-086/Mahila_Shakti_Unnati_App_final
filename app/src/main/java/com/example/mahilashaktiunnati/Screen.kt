package com.example.mahilashaktiunnati

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Navigation routes for all screens in the app.
 */
sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Splash : Screen("splash", "Splash", Icons.Filled.Refresh)
    object Login : Screen("login", "Login", Icons.Filled.Login)
    object Dashboard : Screen("dashboard", "Dashboard", Icons.Filled.Dashboard)
    object Members : Screen("members", "Members", Icons.Filled.Group)
    object Loans : Screen("loans", "Loans", Icons.Filled.AccountBalanceWallet)
    object Reports : Screen("reports", "Reports", Icons.Filled.Assessment)
    object Profile : Screen("profile_settings", "Profile", Icons.Filled.Person)
    object AddMember : Screen("add_member", "Add Member", Icons.Filled.PersonAdd)
    object EditMember : Screen("edit_member/{memberId}", "Edit Member", Icons.Filled.Edit) {
        fun createRoute(memberId: String) = "edit_member/$memberId"
    }
    object MemberProfile : Screen("member_profile/{memberId}", "Profile", Icons.Filled.Person) {
        fun createRoute(memberId: String) = "member_profile/$memberId"
    }
    object SavingsEntry : Screen("savings_entry?memberId={memberId}", "Savings Entry", Icons.Filled.Savings)
    object LoanApplication : Screen("loan_application?memberId={memberId}", "Loan Application", Icons.Filled.MonetizationOn)
    object RepaymentTracker : Screen("repayment_tracker/{loanId}", "Repayment", Icons.Filled.Payment) {
        fun createRoute(loanId: String) = "repayment_tracker/$loanId"
    }
    object Notifications : Screen("notifications", "Notifications", Icons.Filled.Notifications)
}

/** Bottom navigation items (5 tabs) */
val BottomNavItems = listOf(
    Screen.Dashboard,
    Screen.Members,
    Screen.Loans,
    Screen.Reports,
    Screen.Profile
)
