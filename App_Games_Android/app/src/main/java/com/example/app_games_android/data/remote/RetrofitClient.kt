package com.example.app_games_android.data.remote

import com.example.app_games_android.repository.AuthApi
import com.example.app_games_android.repository.ScoreApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    // Creamos una instancia de Retrofit con la URL base de Supabase
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://lvmybcyhrbisfjouhbrx.supabase.co/") // Cambiar por tu URL real
        .client(
            OkHttpClient.Builder().addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("apikey", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imx2bXliY3locmJpc2Zqb3VoYnJ4Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDg1Mjk2NzcsImV4cCI6MjA2NDEwNTY3N30.f2t60RjJh91cNlggE_2ViwPXZ1eXP7zD18rWplSI4jE")
                    .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsImtpZCI6InBZbDlBZDlCckxWQmNLRkYiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2x2bXliY3locmJpc2Zqb3VoYnJ4LnN1cGFiYXNlLmNvL2F1dGgvdjEiLCJzdWIiOiIyMjM3NGEyOC0zMDIyLTRjZWYtYmY4ZS1hMjZhYTAxN2FiYzIiLCJhdWQiOiJhdXRoZW50aWNhdGVkIiwiZXhwIjoxNzQ4NTM5OTg2LCJpYXQiOjE3NDg1MzYzODYsImVtYWlsIjoibW9ydWVAcm9zaGthLmNvbSIsInBob25lIjoiIiwiYXBwX21ldGFkYXRhIjp7InByb3ZpZGVyIjoiZW1haWwiLCJwcm92aWRlcnMiOlsiZW1haWwiXX0sInVzZXJfbWV0YWRhdGEiOnsiZW1haWxfdmVyaWZpZWQiOnRydWV9LCJyb2xlIjoiYXV0aGVudGljYXRlZCIsImFhbCI6ImFhbDEiLCJhbXIiOlt7Im1ldGhvZCI6InBhc3N3b3JkIiwidGltZXN0YW1wIjoxNzQ4NTM2Mzg2fV0sInNlc3Npb25faWQiOiI0MWE2YmVhZS0yYjc5LTRjNWMtOWU4Ny02YTM3ZDA3M2MyMDkiLCJpc19hbm9ueW1vdXMiOmZhbHNlfQ.Lkwyp80dvrFXbkxL-wLzkyBhAPkxUlVDf4vgmDlhtsk")
                    .addHeader("Content-Type", "application/json")
                    .build()
                chain.proceed(request)
            }.build()
        )
        .addConverterFactory(GsonConverterFactory.create()) // Conversor de JSON a objetos Kotlin
        .build()

    // Creamos la instancia del AuthApi usando Retrofit
    val authApi: AuthApi = retrofit.create(AuthApi::class.java)

    // Creamos la instancia del ScoreApi usando Retrofit
    val scoreApi: ScoreApi = retrofit.create(ScoreApi::class.java)
}