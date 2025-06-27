package com.example.app_games_android.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.example.app_games_android.repository.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LoginViewModel : ViewModel() {

    private val _nombre = MutableStateFlow("")
    val nombre: StateFlow<String> = _nombre

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _loginEnabled = MutableLiveData(false)
    val loginEnabled: LiveData<Boolean> = _loginEnabled

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _mensaje = MutableStateFlow("") // Mensaje privado mutable
    val mensaje: StateFlow<String> = _mensaje   // Mensaje expuesto como solo lectura

    fun login(onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            // Pasar parametros para la autenticacion
            val res = AuthRepository.login(_email.value, _password.value)

            _mensaje.value = res.fold(
                onSuccess = {
                    // Extraer nombre de user_metadata del Map
                    val nombreUsuario = (it.user?.get("user_metadata") as? Map<*, *>)?.get("nombre") as? String
                    _nombre.value = nombreUsuario ?: "Usuario"

                    onResult(true)
                    "Login exitoso"
                },
                onFailure = {
                    onResult(false)
                    "Error al iniciar sesión: ${it.message}"
                }
            )
        }
    }

    // Funcion para limpiar los campos al hacer back a Login
    fun limpiarCampos() {
        _email.value = ""
        _password.value = ""
        _nombre.value = ""
        _mensaje.value = ""
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
