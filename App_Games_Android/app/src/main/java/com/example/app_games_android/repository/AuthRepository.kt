package com.example.app_games_android.repository

import com.example.app_games_android.data.model.RespuestaAuth
import com.example.app_games_android.data.model.Usuario
import com.example.app_games_android.data.remote.RetrofitClient

// Contiene lógica para autenticar y registrar usuarios
object AuthRepository {

    suspend fun registrar(usuario: Usuario): Result<RespuestaAuth> = try {
        val respuesta = RetrofitClient.authApi.signUp(usuario)
        Result.success(respuesta) // Devuelve el token si fue exitoso
    } catch (e: Exception) {
        Result.failure(e) // Si algo falla, devuelve el error
    }

    suspend fun login(email: String, password: String): Result<RespuestaAuth> = try {
        val body = mapOf("email" to email, "password" to password)
        val respuesta = RetrofitClient.authApi.login(body)
        Result.success(respuesta)
    } catch (e: Exception) {
        Result.failure(e)
    }

}