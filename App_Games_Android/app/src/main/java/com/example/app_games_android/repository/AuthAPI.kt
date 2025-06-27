package com.example.app_games_android.repository

import com.example.app_games_android.data.model.RespuestaAuth
import com.example.app_games_android.data.model.Usuario
import retrofit2.http.Body
import retrofit2.http.POST

// Define los endpoints de autenticación de Supabase
interface AuthApi {
    @POST("auth/v1/signup")

    suspend fun signUp(@Body usuario: Usuario): RespuestaAuth // Registro

    @POST("auth/v1/token?grant_type=password")

    suspend fun login(@Body credenciales: Map<String, String>): RespuestaAuth // Usamos un mapa simple para login
}