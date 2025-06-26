package com.example.app_games_android.data.model

data class RespuestaAuth(
    val access_token: String?,
    val token_type: String?,
    val user: Map<String, Any>?
)
