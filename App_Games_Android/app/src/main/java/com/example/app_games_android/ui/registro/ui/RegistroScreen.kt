package com.example.app_games_android.ui.registro.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import  androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_games_android.R
import com.example.app_games_android.viewmodel.RegistroViewModel
import kotlinx.coroutines.launch

@Composable
fun RegistroScreen(
    registroViewModel: RegistroViewModel,
    onBackToLogin: () -> Unit
){
    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Registro( Modifier.align(Alignment.Center), registroViewModel, onBackToLogin)
    }
}

@Composable
fun Registro(modifier: Modifier, registroViewModel: RegistroViewModel, onBackToLogin: () -> Unit  ) {

    // Observa los estados del ViewModel usando LiveData con Compose
    val nombre: String by registroViewModel.nombre.observeAsState(initial = "")
    val email: String by registroViewModel.email.observeAsState(initial = "")
    val password: String by registroViewModel.password.observeAsState(initial = "")
    val repeatPass: String by registroViewModel.repeatPass.observeAsState(initial = "")
    val isLoading: Boolean by registroViewModel.isLoading.observeAsState(initial = false)

    // Observar los errores de cada Text Field
    val nombreError by registroViewModel.nombreError.observeAsState()
    val emailError by registroViewModel.emailError.observeAsState()
    val passwordError by registroViewModel.passwordError.observeAsState()
    val repeatPassError by registroViewModel.repeatPassError.observeAsState()

    val estado by registroViewModel.mensaje.collectAsState()


    // Verifica si el usuario ya se registro
    var seRegistro by remember { mutableStateOf(false) }

    // Scope para lanzar corrutinas desde Compose
    val coroutineScope = rememberCoroutineScope()

    // Muestra un loader si isLoading es true
    if(isLoading) {
        Box(Modifier.fillMaxSize()) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    } else {
        // Contenido del formulario
        Column(modifier = modifier) {
            Tittle(Modifier.align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.padding(16.dp))
            HeaderImage(Modifier.align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.padding(16.dp))

            // Campos del formulario: actualizan el estado en el ViewModel
            NombreField(nombre, nombreError) {registroViewModel.onRegistroChanged(it, email, password, repeatPass)}
            Spacer(modifier = Modifier.padding(4.dp))
            EmailField(email, emailError) {registroViewModel.onRegistroChanged(nombre, it, password, repeatPass)}
            Spacer(modifier = Modifier.padding(4.dp))
            PasswordField(password, passwordError) {registroViewModel.onRegistroChanged(nombre, email, it, repeatPass)}
            Spacer(modifier = Modifier.padding(4.dp))
            RepeatPasswordField(repeatPass, repeatPassError) {registroViewModel.onRegistroChanged(nombre, email, password, it)}
            Spacer(modifier = Modifier.padding(16.dp))

            // Botón de "Registrarse" que sigue un hilo
            RegistroButton() {
                coroutineScope.launch {
                    registroViewModel.validarCampos()
                    registroViewModel.registrar(nombre, email, password)
                    registroViewModel.limpiarCampos()
                    registroViewModel.onRegistroSelected()
                    seRegistro = true
                }

            }

            Text(
                text = estado,
                color = if (estado.startsWith("Error")) Color.Red else Color(0xFF0D7E0D),
                fontSize = 14.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 8.dp, bottom = 8.dp)
            )

            // Boton para ir al inicio de Sesion
            if(seRegistro) {
                Button(
                    onClick = { onBackToLogin() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF0D7E0D),
                        contentColor = Color.White
                    )
                ){
                    Text(text = "Ir a Login")
                }
            }
        }
    }
}

@Composable
fun RegistroButton( onRegistroSelected: () -> Unit ) {
    Button(
        onClick = { onRegistroSelected() },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF0D7E0D),
            contentColor = Color.White
        )
    ) {
        Text(text = "Registrarse")
    }
}

@Composable
fun Tittle(modifier: Modifier) {
    Text(
        text = "REGISTRO",
        modifier = modifier,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF0D7E0D)
    )
}

@Composable
fun HeaderImage(modifier: Modifier) {
    Image(painter = painterResource(id = R.drawable.android_games ),
        contentDescription = "Header",
        modifier = modifier
    )
}

@Composable
fun NombreField(nombre: String, nombreError: String?, onTextFieldChanged:(String) -> Unit) {
    TextField(
        value = nombre,
        onValueChange = {onTextFieldChanged(it)},
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Nombre") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        singleLine = true,
        maxLines = 1,
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color(0xFF636262),
            unfocusedContainerColor = Color(0xFFDEDDDD),
            focusedContainerColor = Color(0xFFDEDDDD),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            unfocusedLeadingIconColor = Color.Gray,
            focusedLeadingIconColor = Color.DarkGray
        )
    )
    if (nombreError != null) {
        Text(
            text = nombreError,
            color = Color.Red,
            fontSize = 12.sp
        )
    }
}

@Composable
fun EmailField(email: String, emailError: String?, onTextFieldChanged:(String) -> Unit) {
    TextField(
        value = email,
        onValueChange = {onTextFieldChanged(it)},
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Email") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true,
        maxLines = 1,
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color(0xFF636262),
            unfocusedContainerColor = Color(0xFFDEDDDD),
            focusedContainerColor = Color(0xFFDEDDDD),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            unfocusedLeadingIconColor = Color.Gray,
            focusedLeadingIconColor = Color.DarkGray
        )
    )
    if (emailError != null) {
        Text(
            text = emailError,
            color = Color.Red,
            fontSize = 12.sp
        )
    }
}

@Composable
fun PasswordField(password: String, passwordError: String?, onTextFieldChanged:(String)-> Unit) {
    TextField(
        value = password,
        onValueChange = {onTextFieldChanged(it)},
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Password") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        maxLines = 1,
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color(0xFF636262),
            unfocusedContainerColor = Color(0xFFDEDDDD),
            focusedContainerColor = Color(0xFFDEDDDD),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            unfocusedLeadingIconColor = Color.Gray,
            focusedLeadingIconColor = Color.DarkGray
        )
    )
    if (passwordError != null) {
        Text(
            text = passwordError,
            color = Color.Red,
            fontSize = 12.sp
        )
    }
}

@Composable
fun RepeatPasswordField(repeatPass: String, repeatError: String?, onTextFieldChanged: (String) -> Unit) {
    TextField(
        value = repeatPass,
        onValueChange = {onTextFieldChanged(it)},
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Repetir Password") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        maxLines = 1,
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color(0xFF636262),
            unfocusedContainerColor = Color(0xFFDEDDDD),
            focusedContainerColor = Color(0xFFDEDDDD),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            unfocusedLeadingIconColor = Color.Gray,
            focusedLeadingIconColor = Color.DarkGray
        )
    )

    if (repeatError != null) {
        Text(
            text = repeatError,
            color = Color.Red,
            fontSize = 12.sp
        )
    }
}





