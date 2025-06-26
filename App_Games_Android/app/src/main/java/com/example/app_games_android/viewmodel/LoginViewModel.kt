package com.example.app_games_android.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.example.app_games_android.data.model.Usuario
import com.example.app_games_android.repository.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LoginViewModel : ViewModel() {

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _loginEnabled = MutableLiveData<Boolean>(false)
    val loginEnabled: LiveData<Boolean> = _loginEnabled

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _mensaje = MutableStateFlow("") // Mensaje privado mutable
    val mensaje: StateFlow<String> = _mensaje   // Mensaje expuesto como solo lectura

    fun setEmail(value: String) {
        _email.value = value
    }

    fun setPassword(value: String) {
        _password.value = value
    }

    // Intenta iniciar sesión con credenciales
    fun login(email: String, password: String) {
        viewModelScope.launch {
            val res = AuthRepository.login(Usuario(email, password))
            _mensaje.value = res.fold(
                onSuccess = { "Login exitoso" },
                onFailure = { "Error al iniciar sesión: ${it.message}" }
            )
        }
    }

    // Funcion para limpiar los campos al hacer back a Login
    fun limpiarCampos() {
        _email.value = ""
        _password.value = ""
    }


    // Actualiza los campos y valida si el login puede activarse
    fun onLoginChanged(email: String, password: String) {
        _email.value = email
        _password.value = password

        _loginEnabled.value = isValidEmail(email) && isValidPassword(password)
    }

    // Validaciones
    private fun isValidEmail(email: String): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isValidPassword(password: String): Boolean =
        password.length > 6

    // Simular proceso de login
    fun onLoginSelected(onResult: (Boolean) -> Unit) {
        _isLoading.value = true

        // Simulo delay para login
        runBlocking {
            delay(2000)
            _isLoading.value = false
            onResult(true)  // Devuelve true para login exitoso, podemos cambiar segun nuestra logica real
        }
    }
}
