package com.example.app_games_android.ui.home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.app_games_android.R
import com.example.app_games_android.navigation.Screens
import com.example.app_games_android.viewmodel.LoginViewModel

@Composable
fun HomeScreen(name: String, navController: NavHostController, loginViewModel: LoginViewModel) {
    // Estado del carrusel, define cuántas páginas tiene
    val pagerState = rememberPagerState(pageCount = { 3 })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 40.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(45.dp))

        // Título de bienvenida
        Welcome()

        Spacer(modifier = Modifier.height(30.dp))

        // Muestra el nombre del jugador
        NombreJugador(name)

        Spacer(modifier = Modifier.height(30.dp))

        // Texto que indica al usuario que seleccione un juego
        SeleccionarJuego()

        Spacer(modifier = Modifier.height(45.dp))

        // Carrusel que muestra los juegos disponibles
        HorizontalPagerGames(pagerState)

        Spacer(modifier = Modifier.height(24.dp))

        // Botón "Jugar" solo aparece si no estamos en la portada (índice 0)
        if (pagerState.currentPage != 0) {
            JugarButton(onClick = {
                when (pagerState.currentPage) {
                    1 -> navController.navigate(Screens.Poker.createRoute(name))
                    2 -> navController.navigate(Screens.Tocame.createRoute(name))
                }
            })
        }

        // Fila inferior con botones de Puntaje y Ayuda
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            PuntajeButton()
            AyudaButton(juegoActual = pagerState.currentPage)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Boton para cerrar sesion que contiene un back
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            CerrarSesionButton(
                onCerrarSesion = {
                    loginViewModel.limpiarCampos()
                    navController.popBackStack(route = "login", inclusive = false)
                }
            )
        }
    }
}

@Composable
fun Welcome() {
    Text(
        text = "BIENVENIDA",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFFFF9800),
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}

@Composable
fun NombreJugador(name: String) {
    Text(
        text = name,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFFFF9800),
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}

@Composable
fun SeleccionarJuego() {
    Text(
        text = "Selecciona un Juego",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFFFF9800),
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}

@Composable
fun HeaderImage(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.game_logo),
        contentDescription = "Header",
        modifier = modifier
    )
}

@Composable
fun HorizontalPagerGames(pagerState: PagerState) {
    // Carrusel de juegos (3 páginas)
    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) { page ->
        // Contenido según la página actual
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (page) {
                // Imagen de portada del carrusel
                0 -> HeaderImage(
                    Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp))
                )
                // Imagen que lleva a la pantalla de poker
                1 -> GenerateImage(
                    Modifier
                        .clip(CircleShape)
                        .fillMaxHeight(),
                    R.drawable.poker,
                    ContentScale.Crop
                )
                // Imagen que lleva a la pantalla de Tocame
                2 -> GenerateImage(
                    Modifier
                        .clip(CircleShape)
                        .fillMaxHeight(),
                    R.drawable.tocame,
                    ContentScale.Crop
                )
            }
        }
    }
}

@Composable
fun GenerateImage(
    modifier: Modifier = Modifier,
    imageResId: Int,
    contentScale: ContentScale = ContentScale.Fit
) {
    // Componente reutilizable para mostrar imágenes con estilo
    Image(
        painter = painterResource(id = imageResId),
        contentDescription = null,
        modifier = modifier,
        contentScale = contentScale
    )
}

@Composable
fun JugarButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFFF9800),
            disabledContainerColor = Color(0xFFFFC107),
            contentColor = Color.White,
            disabledContentColor = Color.White
        )
    ) {
        Text(
            text = "Jugar",
            fontSize = 20.sp,
        )
    }
}

@Composable
fun PuntajeButton() {
    Button(
        onClick = { /* A implementar */ },
        modifier = Modifier
            .width(180.dp)
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFFF9800),
            contentColor = Color.White,
        )
    ) {
        Text(
            text = "Puntajes",
            fontSize = 20.sp,
        )
    }
}

@Composable
fun AyudaButton(juegoActual: Int) {

    // Boton para mostrar las instrucciones del juego seleccionado
    var mostrarDialogo by remember { mutableStateOf(false) }

    Button(
        onClick = { mostrarDialogo = true },
        modifier = Modifier
            .width(180.dp)
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFFF9800),
            contentColor = Color.White,
        )
    ) {
        Text(
            text = "Ayuda",
            fontSize = 20.sp,
        )
    }

    if (mostrarDialogo) {

        val instrucciones = when (juegoActual) {
            1 -> "Poker:\n\nEl objetivo del juego es formar la mejor mano posible combinando cinco cartas."
            2 -> "Tocame:\n\nToca el osito en movimiento lo más rápido posible para sumar puntos."
            else -> "Instrucciones no disponibles para este juego."
        }

        // Diálogo emergente con las instrucciones
        AlertDialog(
            onDismissRequest = { mostrarDialogo = false },
            title = { Text("Instrucciones") },
            text = { Text(instrucciones) },
            confirmButton = {
                Button(onClick = { mostrarDialogo = false }) {
                    Text("Entendido")
                }
            }
        )
    }
}

@Composable
fun CerrarSesionButton( onCerrarSesion: () -> Unit ) {
    // Boton con un back para cerrar sesion
    Button(
        onClick = { onCerrarSesion() },
        modifier = Modifier
            .width(180.dp)
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFFF9800),
            contentColor = Color.White,
        )
    ) {
        Text(
            text = "Cerrar Sesion",
            fontSize = 20.sp,
        )
    }
}
