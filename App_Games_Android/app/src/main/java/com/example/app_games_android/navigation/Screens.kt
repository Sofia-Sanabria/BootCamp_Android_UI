package com.example.app_games_android.navigation

sealed class Screens(val route: String) {
    data object Login : Screens("login")
    data object Registro : Screens("registro")
    data object Home : Screens("home")
}
