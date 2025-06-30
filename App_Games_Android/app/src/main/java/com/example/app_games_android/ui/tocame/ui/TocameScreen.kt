package com.example.app_games_android.ui.tocame.ui

import android.app.AlertDialog
import android.content.Context
import android.os.CountDownTimer
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_games_android.R
import androidx.navigation.NavController

@Composable
fun TocameScreen(
    jugadorNombre: String,
    navController: NavController
) {
    // Estado para el puntaje actual
    var puntaje by remember { mutableIntStateOf(0) }

    // Estado para el tiempo restante
    var tiempo by remember { mutableIntStateOf(10) }

    // Estado para saber si el juego está activo
    var juegoActivo by remember { mutableStateOf(false) }

    // Controlador del temporizador
    var timer: CountDownTimer? by remember { mutableStateOf(null) }

    // Tamaño del área de juego
    val context = LocalContext.current
    val density = LocalDensity.current

    // Estado para la posición del osito
    var posX by remember { mutableFloatStateOf(100f) }
    var posY by remember { mutableFloatStateOf(100f) }

    // Crear imagen del osito como recurso
    val osito = painterResource(id = R.drawable.osito_img)

    // Efecto cuando empieza el juego
    LaunchedEffect(juegoActivo) {
        if (juegoActivo) {
            // Reiniciar el puntaje y el tiempo
            puntaje = 0
            tiempo = 10

            // Iniciar el temporizador
            timer = object : CountDownTimer(10000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    tiempo--
                }

                override fun onFinish() {
                    juegoActivo = false
                    timer?.cancel()
                    timer = null

                    // Lógica para enviar el puntaje y mostrar alerta
                    mostrarAlertaFinJuego(context, jugadorNombre, puntaje, navController)
                }
            }.start()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 40.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 25.dp)
        ) {
            // Mostrar nombre del jugador
            Text(
                text = jugadorNombre,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )

            // Mostrar puntaje
            Text(
                text = "Puntaje: $puntaje",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold

            )

            // Mostrar tiempo
            Text(
                text = "Tiempo: $tiempo",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .size(50.dp)
                .background(Color(0xFFFFC0CB))
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            // Botón de iniciar juego
            Button(
                onClick = { juegoActivo = true },
                enabled = !juegoActivo,
            ) {
                Text(
                    "Iniciar Juego"
                )
            }

            // Imagen del osito (solo se ve si el juego está activo)
            if (juegoActivo) {
                Image(
                    painter = osito,
                    contentDescription = "Osito",
                    modifier = Modifier
                        .size(80.dp)
                        .offset { IntOffset(posX.toInt(), posY.toInt()) }
                        .clip(CircleShape)
                        .background(Color.White)
                        .clickable {
                            // Aumentar puntaje al tocar el osito
                            puntaje++

                            // Calcular nueva posición aleatoria
                            val maxX = context.resources.displayMetrics.widthPixels - 200
                            val maxY = context.resources.displayMetrics.heightPixels - 400

                            posX = (0..maxX).random().toFloat()
                            posY = (0..maxY).random().toFloat()
                        }
                )
            }

            // Botón para ver Top 5 puntajes
            Button(
                onClick = {
                    navController.navigate("top5") // Ruta a la pantalla de puntajes
                }
            ) {
                Text(
                    "Top 5",

                    )
            }
        }
    }
}

fun mostrarAlertaFinJuego(
    context: Context,
    jugador: String,
    puntaje: Int,
    navController: NavController
) {
    val builder = AlertDialog.Builder(context)
    builder.setTitle("¡Fin del juego!")
    builder.setMessage("$jugador, tu puntaje fue: $puntaje")

    builder.setPositiveButton("Ver mejores puntajes") { _, _ ->
        navController.navigate("top5")
    }

    builder.setNegativeButton("Volver a jugar") { dialog, _ ->
        dialog.dismiss()
    }

    builder.show()
}

