package com.example.app_games_android.ui.login.ui

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_games_android.R
import kotlinx.coroutines.launch

@Composable
fun RegistroScreen(viewModel: RegistroViewModel) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Registro(Modifier.align(Alignment.Center), viewModel)
    }
}

@Composable
fun Registro(modifier: Modifier, viewModel: RegistroViewModel) {

    // Observa los estados del ViewModel usando LiveData con Compose
    val nombre: String by viewModel.nombre.observeAsState(initial = "")
    val email: String by viewModel.email.observeAsState(initial = "")
    val password: String by viewModel.password.observeAsState(initial = "")
    val repeatPass: String by viewModel.repeatPass.observeAsState(initial = "")
    val registroEnable: Boolean by viewModel.registroEnable.observeAsState(initial = false) // El boton inicia deshabilitado
    val isLoading: Boolean by viewModel.isLoading.observeAsState(initial = false)

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
            NombreField(nombre) {viewModel.onRegistroChanged(it, email, password, repeatPass)}
            Spacer(modifier = Modifier.padding(4.dp))
            EmailField(email) {viewModel.onRegistroChanged(nombre, it, password, repeatPass)}
            Spacer(modifier = Modifier.padding(4.dp))
            PasswordField(password) {viewModel.onRegistroChanged(nombre, email, it, repeatPass)}
            Spacer(modifier = Modifier.padding(4.dp))
            RepeatPasswordField(repeatPass) {viewModel.onRegistroChanged(nombre, email, password, it)}
            Spacer(modifier = Modifier.padding(16.dp))

            // Botón de "Registrarse" que sigue un hilo
            RegistroButton(registroEnable) {
                coroutineScope.launch {
                    viewModel.onRegistroSelected()
                }
            }
        }
    }
}

// Botón que se habilita solo si todos los datos son válidos
@Composable
fun RegistroButton(registroEnable: Boolean, onRegistroSelected: () -> Unit) {
    Button(
        onClick = { onRegistroSelected() },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF0D7E0D),
            disabledContainerColor = Color(0xFF77BD6E),
            contentColor = Color.White,
            disabledContentColor = Color.White
        ), enabled = registroEnable // se habilita según el estado
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
fun NombreField(nombre: String, onTextFieldChanged:(String) -> Unit) {
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
}

@Composable
fun EmailField(email: String, onTextFieldChanged:(String) -> Unit) {
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
}

@Composable
fun PasswordField(password: String, onTextFieldChanged:(String)-> Unit) {
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
}

@Composable
fun RepeatPasswordField(repeatPass: String, onTextFieldChanged: (String) -> Unit) {
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
}





