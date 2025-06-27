package com.example.app_games_android.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.example.app_games_android.data.model.Metadata
import com.example.app_games_android.data.model.Usuario
import com.example.app_games_android.repository.AuthRepository
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegistroViewModel: ViewModel() {

    // LiveData para almacenar los campos del formulario
    private val _nombre = MutableLiveData("")
    val nombre: LiveData<String> = _nombre // ':' siginifica, de tipo ...

    private val _email = MutableLiveData("")
    val email: LiveData<String> = _email

    private val _password = MutableLiveData("")
    val password: LiveData<String> = _password

    private val _repeatPass = MutableLiveData("")
    val repeatPass: LiveData<String> = _repeatPass

    // Estado de carga para mostrar el CircularProgressIndicator
    private val _isLoading  = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _mensaje = MutableStateFlow("") // Mensaje privado mutable
    val mensaje: StateFlow<String> = _mensaje   // Mensaje expuesto como solo lectura

    // Intenta registrar un nuevo usuario
    fun registrar(nombre: String, email: String, password: String) {
        viewModelScope.launch {
            val usuario = Usuario(
                email = email,
                password = password,
                data = Metadata(nombre = nombre) // Incluimos nombre como metadata
            )

            val res = AuthRepository.registrar(usuario)
            _mensaje.value = res.fold(
                onSuccess = { "Registro Exitoso"},
                onFailure = {  "Error al registrar: ${it.message}" }
            )
        }
    }

    /**
     * Esta función se llama cada vez que el usuario escribe algo
     * en los campos del formulario. Actualiza los valores y valida.
     */
    fun onRegistroChanged(nombre: String, email: String, password: String, repeatPass: String) {
        _nombre.value = nombre
        _email.value = email
        _password.value = password
        _repeatPass.value = repeatPass
    }

    /**
     * LiveData para mostrar mensajes de error
     */

    private val _nombreError = MutableLiveData<String?>()
    val nombreError: LiveData<String?> = _nombreError

    private val _emailError = MutableLiveData<String?>()
    val emailError: LiveData<String?> = _emailError

    private val _passwordError = MutableLiveData<String?>()
    val passwordError: LiveData<String?> = _passwordError

    private val _repeatPassError = MutableLiveData<String?>()
    val repeatPassError: LiveData<String?> = _repeatPassError


    /**
     * Funciones de Validacion de Formulario
     */
    fun isValidNombre(nombre: String): Boolean = nombre.isNotBlank() && nombre.length >= 2

    fun isValidEmail(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun isValidPassword(password: String): Boolean = password.length > 6

    fun isValidRepeatPass(repeatPass: String): Boolean = repeatPass == _password.value && repeatPass.length > 6

    /**
     * Funcion para limpiar los campos
     */
    fun limpiarCampos() {
        _nombre.value = ""
        _email.value = ""
        _password.value = ""
        _repeatPass.value = ""
    }


    /**
     * Funcion para validar todos los campos
     */
    fun validarCampos(): Boolean {
        var valido = true

        if (!isValidNombre(_nombre.value ?: "")) {
            _nombreError.value = "El nombre debe tener al menos 2 letras"
            valido = false
        } else {
            _nombreError.value = null
        }

        if (!isValidEmail(_email.value ?: "")) {
            _emailError.value = "Correo inválido"
            valido = false
        } else {
            _emailError.value = null
        }

        if (!isValidPassword(_password.value ?: "")) {
            _passwordError.value = "La contraseña debe tener más de 6 caracteres"
            valido = false
        } else {
            _passwordError.value = null
        }

        if (!isValidRepeatPass(_repeatPass.value ?: "")) {
            _repeatPassError.value = "Las contraseñas no coinciden"
            valido = false
        } else {
            _repeatPassError.value = null
        }

        return valido
    }

    /**
     * Funcion corrutina que sigue un hilo para simular un registro
     */
    suspend fun onRegistroSelected() {
        _isLoading.value = true
        delay(1.seconds)
        _isLoading.value = false
    }
}