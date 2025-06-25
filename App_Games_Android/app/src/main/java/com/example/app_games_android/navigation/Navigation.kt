package com.example.app_games_android.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app_games_android.ui.login.ui.LoginViewModel
import com.example.app_games_android.ui.login.ui.LoginScreen
import com.example.app_games_android.ui.login.ui.RegistroScreen
import com.example.app_games_android.ui.login.ui.RegistroViewModel
import com.example.app_games_android.ui.home.ui.HomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.Login.route) {
        composable(route = Screens.Login.route) {
            val loginViewModel: LoginViewModel = viewModel()
            LoginScreen(
                { navController.navigate(Screens.Registro.route) },
                { navController.navigate(Screens.Home.route) },
                loginViewModel
            )
        }

        composable(route = Screens.Registro.route) {
            val registroViewModel: RegistroViewModel = viewModel()
            RegistroScreen(registroViewModel)
        }

        composable(route = Screens.Home.route) {
            HomeScreen()
        }
    }
}

