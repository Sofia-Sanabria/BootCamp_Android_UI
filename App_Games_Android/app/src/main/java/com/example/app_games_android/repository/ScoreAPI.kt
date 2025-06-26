package com.example.app_games_android.repository

import com.example.app_games_android.data.model.Puntaje
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

// Define los endpoints para registrar y obtener puntajes
interface ScoreApi {
    @POST("rest/v1/score")
    suspend fun subirPuntaje(@Body puntaje: Puntaje) // Enviar puntaje nuevo

    @GET("rest/v1/score")
    suspend fun obtenerPuntajes(@Query("select") select: String = "*"): List<Puntaje> // Consultar todos
}
