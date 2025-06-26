package com.example.app_games_android.data.remote

import com.example.app_games_android.repository.AuthApi
import com.example.app_games_android.repository.ScoreApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    // Creamos una instancia de Retrofit con la URL base de Supabase
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://lvmybcyhrbisfjouhbrx.supabase.co/") // Cambiar por tu URL real
        .addConverterFactory(GsonConverterFactory.create()) // Conversor de JSON a objetos Kotlin
        .build()

    // Creamos la instancia del AuthApi usando Retrofit
    val authApi: AuthApi = retrofit.create(AuthApi::class.java)

    // Creamos la instancia del ScoreApi usando Retrofit
    val scoreApi: ScoreApi = retrofit.create(ScoreApi::class.java)
}