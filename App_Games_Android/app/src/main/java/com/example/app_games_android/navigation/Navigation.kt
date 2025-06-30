package com.example.app_games_android.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.app_games_android.viewmodel.LoginViewModel
import com.example.app_games_android.ui.login.ui.LoginScreen
import com.example.app_games_android.ui.registro.ui.RegistroScreen
import com.example.app_games_android.viewmodel.RegistroViewModel
import com.example.app_games_android.ui.home.ui.HomeScreen
import com.example.app_games_android.ui.poker.ui.PokerScreen
import com.example.app_games_android.ui.tocame.ui.TocameScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.Login.route
    ) {

        // Pantalla de Login
        composable(route = Screens.Login.route) {
            val loginViewModel: LoginViewModel = viewModel()

            // Al presionar login exitoso, se navega a Home pasando el nombre
            LoginScreen(
                onLoginSuccess = { name ->
                    navController.navigate(Screens.Home.createRoute(name))
                },
                onNavigateToRegister = {
                    navController.navigate(Screens.Registro.route)
                },
                loginViewModel = loginViewModel
            )
        }

        // Pantalla de Registro
        composable(route = Screens.Registro.route) {
            val registroViewModel: RegistroViewModel = viewModel()

            RegistroScreen(
                registroViewModel = registroViewModel,
                onBackToLogin = { navController.popBackStack() }
            )
        }

        // Pantalla de Home con argumento 'name'
        composable(
            route = Screens.Home.route,
            arguments = listOf(navArgument("name") { type = NavType.StringType })
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val loginViewModel: LoginViewModel = viewModel()
            HomeScreen(
                name = name,
                navController = navController,
                loginViewModel = loginViewModel
            )
        }

        // Pantalla de Tocame con argumento 'name'
        composable(
            route = Screens.Tocame.route,
            arguments = listOf(navArgument("name") { type = NavType.StringType })
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            TocameScreen(jugadorNombre = name, navController = navController)
        }

        // Pantalla de Poker con argumento 'name'
        composable(
            route = Screens.Poker.route,
            arguments = listOf(navArgument("name") { type = NavType.StringType })
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            PokerScreen(jugadorNombre = name, navController = navController)
        }
    }

}

