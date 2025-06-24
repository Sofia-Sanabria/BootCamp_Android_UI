package com.example.app_games_android.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.livedata.observeAsState
import kotlinx.coroutines.launch
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.Icons

@Composable
fun LoginScreen(navController: NavController, loginViewModel: LoginViewModel = viewModel()) {
    // Traigo los datos del ViewModel (email, password, si el login está habilitado y si está cargando)
    val email: String by loginViewModel.email.observeAsState("")
    val password: String by loginViewModel.password.observeAsState("")
    val loginEnabled: Boolean by loginViewModel.loginEnabled.observeAsState(false)
    val isLoading: Boolean by loginViewModel.isLoading.observeAsState(false)

    // Para lanzar corutinas cuando toque hacer login
    val coroutineScope = rememberCoroutineScope()
    // variable para controlar la visibilidad
    var passwordVisible by remember { mutableStateOf(false) }

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
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campo para ingresar email
                TextField(
                    value = email,
                    onValueChange = { loginViewModel.onLoginChanged(it, password) },
                    label = { Text("Email") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Campo para ingresar contraseña
                TextField(
                    value = password,
                    onValueChange = { loginViewModel.onLoginChanged(email, it) },
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

                // Botón para iniciar sesion
                Button(
                    onClick = {
                        coroutineScope.launch {
                            // Llamo al ViewModel para procesar el login
                            loginViewModel.onLoginSelected { success ->
                                if (success) {
                                    // Acá podes mostrar mensaje o navegar a otra pantalla
                                    println("Login Exitoso")
                                } else {
                                    // Mostrar error si falla login
                                    println("Login error")
                                }
                            }
                        }
                    },
                    enabled = loginEnabled,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Iniciar Sesión")
                }
                //agrego boton de registrarse y texto
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("¿No tienes una cuenta?")
                TextButton(onClick = { navController.navigate("registro")  }) {
                    Text("Registrarse")
                    }
                }
            }
        }
    }
}
