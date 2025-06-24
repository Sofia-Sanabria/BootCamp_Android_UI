package com.example.app_games_android.ui.login.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.delay

class RegistroViewModel: ViewModel() {

    // LiveData para almacenar los campos del formulario
    private val _nombre = MutableLiveData<String>("")
    val nombre: LiveData<String> = _nombre // ':' siginifica, de tipo ...

    private val _email = MutableLiveData("")
    val email: LiveData<String> = _email

    private val _password = MutableLiveData("")
    val password: LiveData<String> = _password

    private val _repeatPass = MutableLiveData("")
    val repeatPass: LiveData<String> = _repeatPass

    // Estado que determina si el botón "Registrarse" está habilitado
    private val _registroEnable = MutableLiveData(false)
    val registroEnable: LiveData<Boolean> = _registroEnable

    // Estado de carga para mostrar el CircularProgressIndicator
    private val _isLoading  = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    /**
     * Esta función se llama cada vez que el usuario escribe algo
     * en los campos del formulario. Actualiza los valores y valida.
     */
    fun onRegistroChanged(nombre: String, email: String, password: String, repeatPass: String) {
        _nombre.value = nombre
        _email.value = email
        _password.value = password
        _repeatPass.value = repeatPass

        // Actualizamos el estado del botón si todos los campos son válidos
        _registroEnable.value =
            isValidNombre(nombre)
            && isValidEmail(email)
            && isValidPassword(password)
            && isValidRepeatPass(repeatPass)
    }

    /**
     * Funciones de Validacion de Formulario
     */
    fun isValidNombre(nombre: String): Boolean = nombre.isNotBlank() && nombre.length >= 2

    fun isValidEmail(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun isValidPassword(password: String): Boolean = password.length > 6

    fun isValidRepeatPass(repeatPass: String): Boolean = repeatPass == _password.value && repeatPass.length > 6


    // Una funcion corrutina (Sigue un hilo)
    suspend fun onRegistroSelected() {
        _isLoading.value = true
        delay(4.seconds)
        _isLoading.value = false
    }
}