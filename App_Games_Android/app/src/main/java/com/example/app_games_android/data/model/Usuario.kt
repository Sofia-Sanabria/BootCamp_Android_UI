package com.example.app_games_android.data.model

data class Usuario(
    val email: String,
    val password: String,
    val data:  Metadata
)

// Metadata para registrar Usuarios con nombre
data class Metadata(
    val nombre: String
)
