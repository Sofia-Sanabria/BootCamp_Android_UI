package com.example.app_games_android.ui.login.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import kotlinx.coroutines.delay
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
