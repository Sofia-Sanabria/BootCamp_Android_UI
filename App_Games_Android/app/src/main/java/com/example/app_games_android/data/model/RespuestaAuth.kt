package com.example.app_games_android.data.model

// Representa la respuesta que devuelve Supabase al autenticar o registrar
// (puede incluir access_token, tipo de token y datos del usuario)
data class RespuestaAuth(
    val access_token: String?,
    val token_type: String?,
    val user: Map<String, Any>?
)

