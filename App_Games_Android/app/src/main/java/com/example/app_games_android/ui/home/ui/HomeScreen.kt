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

@Composable
fun HomeScreen(name: String, navController: NavHostController) {
    val pagerState = rememberPagerState(pageCount = { 3 })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 40.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(45.dp))
        Welcome()
        Spacer(modifier = Modifier.height(30.dp))
        NombreJugador(name)
        Spacer(modifier = Modifier.height(30.dp))
        SeleccionarJuego ()
        Spacer(modifier = Modifier.height(45.dp))

        // Carrusel de juegos
        HorizontalPagerGames(pagerState)

        Spacer(modifier = Modifier.height(24.dp))

        // Mostrar botón solo si no estamos en la portada
        if (pagerState.currentPage != 0) {
            JugarButton(onClick = {
                when (pagerState.currentPage) {
                    1 -> navController.navigate("poker")
                    2 -> navController.navigate("tocame")
                }
            })
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            PuntajeButton()
            AyudaButton(juegoActual = pagerState.currentPage)
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
fun SeleccionarJuego () {
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
        painter = painterResource(id = R.drawable.game_logo ),
        contentDescription = "Header",
        modifier = modifier
    )
}

@Composable
fun HorizontalPagerGames(pagerState: PagerState) {
    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) { page ->
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (page) {
                0 -> HeaderImage(
                    Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp))
                )
                1 -> GenerateImage(
                    Modifier
                        .clip(CircleShape)
                        .fillMaxHeight(),
                    R.drawable.poker,
                    ContentScale.Crop
                )
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
        onClick = { },
        modifier = Modifier
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFFF9800),
            contentColor = Color.White,
        )
    ) {
        Text(
            text = "Puntaje",
            fontSize = 20.sp,
        )
    }
}

@Composable
fun AyudaButton(juegoActual: Int) {
    var mostrarDialogo by remember { mutableStateOf(false) }

    Button(
        onClick = { mostrarDialogo = true },
        modifier = Modifier.height(48.dp),
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