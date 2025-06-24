package com.example.app_games_android.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.app_games_android.ui.login.LoginScreen
import com.example.app_games_android.ui.login.ui.RegistroScreen
import com.example.app_games_android.ui.login.ui.RegistroViewModel

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController = navController)
        }
        composable("registro") {
            val registroViewModel: RegistroViewModel = viewModel()
            RegistroScreen(navController = navController, viewModel = registroViewModel)
        }
    }
}
