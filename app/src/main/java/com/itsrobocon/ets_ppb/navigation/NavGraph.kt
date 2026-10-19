package com.itsrobocon.ets_ppb.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.itsrobocon.ets_ppb.feature_member.ui.detail.MemberDetailScreen
import com.itsrobocon.ets_ppb.feature_member.ui.list.MemberListScreen
import com.itsrobocon.ets_ppb.feature_member.ui.registration.RegistrationScreen

@Composable
fun LibraryNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.MemberList.route
    ) {
        composable(Screen.MemberList.route) {
            MemberListScreen(
                onNavigateToRegistration = {
                    navController.navigate(Screen.Registration.route)
                },
                onNavigateToDetail = { memberId, isEdit ->
                    navController.navigate(Screen.MemberDetail.createRoute(memberId, isEdit))
                }
            )
        }

        composable(Screen.Registration.route) {
            RegistrationScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.MemberDetail.route,
            arguments = listOf(
                navArgument("memberId") {
                    type = NavType.IntType
                },
                navArgument("isEdit") {
                    type = NavType.BoolType
                    defaultValue = false
                }
            )
        ) { backStackEntry ->
            val memberId = backStackEntry.arguments?.getInt("memberId") ?: return@composable
            val isEdit = backStackEntry.arguments?.getBoolean("isEdit") ?: false
            MemberDetailScreen(
                memberId = memberId,
                initialIsEdit = isEdit,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
