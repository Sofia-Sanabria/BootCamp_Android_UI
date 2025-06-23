package com.example.app_games_android.ui.login.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.delay

class RegistroViewModel: ViewModel() {

    // Crear LiveData para manejar TextFiel
    private val _nombre = MutableLiveData<String>()
    val nombre: LiveData<String> = _nombre // ':' siginifica, de tipo ...
    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email
    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password
    private val _repeatPass = MutableLiveData<String>()
    val repeatPass: LiveData<String> = _repeatPass

    // Valor que va habilitar o dehabilitar el boton de Registro
    private val _registroEnable = MutableLiveData<Boolean>()
    val registroEnable: LiveData<Boolean> = _registroEnable

    // Valor de carga de pagina
    private val _isLoading  = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun onRegistroChanged(nombre: String, email: String, password: String, repeatPass: String) {
        _nombre.value = nombre
        _email.value = email
        _password.value = password
        _repeatPass.value = repeatPass

        _registroEnable.value =
            isValidNombre(nombre)
            && isValidEmail(email)
            && isValidPassword(password)
            && isValidRepeatPass(repeatPass)

    }

    // Funcion para validar un nombre
    fun isValidNombre(nombre: String): Boolean = nombre.isNotBlank() && nombre.length >= 2

    fun isValidEmail(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun isValidPassword(password: String): Boolean = password.length > 6

    fun isValidRepeatPass(repeatPass: String) : Boolean = repeatPass.length > 6

    // Una funcion corrutina (Sigue un hilo)
    suspend fun onRegistroSelected() {
        _isLoading.value = true
        delay(4.seconds)
        _isLoading.value = false
    }
}