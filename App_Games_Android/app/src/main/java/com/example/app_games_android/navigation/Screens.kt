package com.example.app_games_android.navigation

// Clase sellada para centralizar las rutas de navegación
sealed class Screens(val route: String) {

    data object Login : Screens("login")
    data object Registro : Screens("registro")

    // Pantalla de Home con un parámetro 'name' que se pasará en la ruta
    data object Home : Screens("home/{name}") {
        fun createRoute(name: String) = "home/$name"
    }
}
