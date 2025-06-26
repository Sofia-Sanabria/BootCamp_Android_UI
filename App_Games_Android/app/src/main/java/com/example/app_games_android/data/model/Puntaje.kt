package com.example.app_games_android.data.model

data class Puntaje(
    val user_id: String,  // o "email", según cómo manejes el score
    val juego: String,
    val puntaje: Int
)
