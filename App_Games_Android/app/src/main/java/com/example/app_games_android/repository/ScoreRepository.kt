package com.example.app_games_android.repository

import com.example.app_games_android.data.model.Puntaje
import com.example.app_games_android.data.remote.RetrofitClient

// Contiene lógica para registrar y obtener puntajes
object ScoreRepository {

    suspend fun subirPuntaje(puntaje: Puntaje): Result<Unit> = try {
        RetrofitClient.scoreApi.subirPuntaje(puntaje)
        Result.success(Unit) // Si se envió correctamente
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun obtenerPuntajes(): Result<List<Puntaje>> = try {
        val lista = RetrofitClient.scoreApi.obtenerPuntajes()
        Result.success(lista)
    } catch (e: Exception) {
        Result.failure(e)
    }
}