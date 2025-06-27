package com.example.app_games_android.ui.login.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app_games_android.viewmodel.LoginViewModel
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(
    onLoginSuccess: (String) -> Unit,     // Navega a Home con el nombre
    onNavigateToRegister: () -> Unit,     // Navega a Registro
    loginViewModel: LoginViewModel = viewModel()
) {

    // Traigo los datos del ViewModel (email, password, si el login está habilitado y si está cargando)
    val email by loginViewModel.email.collectAsState()
    val password by loginViewModel.password.collectAsState()
    val mensaje by loginViewModel.mensaje.collectAsState()

    val loginEnabled: Boolean by loginViewModel.loginEnabled.observeAsState(false)
    val isLoading: Boolean by loginViewModel.isLoading.observeAsState(false)

    // Para lanzar corutinas cuando toque hacer login
    val coroutineScope = rememberCoroutineScope()

    // variable para controlar la visibilidad
    var passwordVisible by remember { mutableStateOf(false) }

    // Se asegura de recomponer todos los campos de login
    LaunchedEffect(Unit) {
        loginViewModel.limpiarCampos()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Si estamos cargando, muestro el circulo de progreso
        if (isLoading) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        } else {
            Column(
                modifier = Modifier.align(Alignment.Center),
                verticalArrangement = Arrangement.Center
            ) {
                // Título del login
                Text(
                    text = "LOGIN",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D407E)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Campo para ingresar email
                TextField(
                    value = email,
                    onValueChange = { loginViewModel.onLoginChanged(it, password)},
                    label = { Text("Email") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Campo para ingresar contraseña
                TextField(
                    value = password,
                    onValueChange = { loginViewModel.onLoginChanged(email, it)},
                    label = { Text("Password") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    singleLine = true,
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val icon = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = icon, contentDescription = null)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Botón para iniciar sesión
                Button(
                    onClick = {
                        coroutineScope.launch {
                            loginViewModel.login { success ->
                                if (success) {
                                    val nombre = loginViewModel.nombre.value
                                    onLoginSuccess(nombre)
                                }
                            }
                        }
                    },
                    // Habilitado solo si hay texto
                    enabled = email.isNotBlank() && password.isNotBlank(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Text("Iniciar Sesión")
                }


                // Mostrar mensaje
                Text(
                    text = mensaje,
                    color = if (mensaje.startsWith("Error")) Color.Red else Color.Green,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )

                //agrego boton de registrarse y texto
                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("¿No tienes una cuenta?")
                        TextButton(onClick = { onNavigateToRegister() }) {
                            Text("Registrarse")
                        }
                    }
                }

            }
        }
    }
}
