package com.example.app_games_android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_games_android.repository.ScoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// ViewModel para manejar los puntajes
class ScoreViewModel : ViewModel() {

    private val _estado = MutableStateFlow("") // Estado interno del mensaje
    val estado: StateFlow<String> = _estado     // Estado expuesto a la UI

    // Envía un puntaje al servidor
    fun guardarPuntaje(userId: String, juego: String, puntaje: Int) {
        viewModelScope.launch {
            val res = ScoreRepository.subirPuntaje(
                com.example.app_games_android.data.model.Puntaje(userId, juego, puntaje)
            )
            _estado.value = res.fold(
                onSuccess = { "Puntaje guardado" },
                onFailure = { "Error: ${it.message}" }
            )
        }
    }

    // Carga todos los puntajes del backend
    fun cargarPuntajes() {
        viewModelScope.launch {
            val res = ScoreRepository.obtenerPuntajes()
            _estado.value = res.fold(
                onSuccess = { "Puntajes cargados: ${it.size}" },
                onFailure = { "Error al obtener puntajes: ${it.message}" }
            )
        }
    }
}
