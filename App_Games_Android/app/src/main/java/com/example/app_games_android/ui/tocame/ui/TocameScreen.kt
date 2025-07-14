package com.example.app_games_android.ui.tocame.ui

import android.app.AlertDialog
import android.content.Context
import android.os.CountDownTimer
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_games_android.R
import androidx.navigation.NavController

@Composable
fun TocameScreen(
    name: String,
    navController: NavController
) {
    // Estado para el puntaje actual
    var puntaje by remember { mutableIntStateOf(0) }

    // Estado para el tiempo restante
    var tiempo by remember { mutableIntStateOf(10) }

    // Estado para saber si el juego está activo
    var juegoActivo by remember { mutableStateOf(false) }

    // Guardamos la referencia del temporizador para poder cancelarlo
    val timer = remember { mutableStateOf<CountDownTimer?>(null) }
    // Tamaño del área de juego
    val context = LocalContext.current
    val density = LocalDensity.current

    val anchoJuego = 300.dp
    val altoJuego = 600.dp

    var posX by remember { mutableFloatStateOf(0f) }
    var posY by remember { mutableFloatStateOf(0f) }

    // Crear imagen del osito como recurso
    val osito = painterResource(id = R.drawable.osito_img)

    @Composable
    fun NombreJugador(name: String) {
        Text(
            text = name,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Magenta
        )
    }

    @Composable
    fun PuntajeJugador(puntaje: Int) {
        Text(
            text = "$puntaje",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Magenta
        )
    }

    @Composable
    fun TiempoJuego(tiempo: Int) {
        Text(
            text = "$tiempo",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Magenta
        )
    }

    @Composable
    fun TablaDeJuego(
        ancho: Dp = 300.dp,
        alto: Dp = 400.dp,
        ositoX: Float,
        ositoY: Float,
        onOsitoTocado: () -> Unit
    ) {
        Box(
            modifier = Modifier
                .size(width = ancho, height = alto) // Fija el tamaño del área
                .background(Color(0xFFFFF0F5))       // Color rosado bebé
                .border(2.dp, Color.Magenta)         // Opcional: borde para ver el área
                .clip(RoundedCornerShape(16.dp))     // Bordes redondeados
                .padding(8.dp)
        ) {
            if(juegoActivo) {
                Image(
                    painter = osito,
                    contentDescription = "Osito",
                    modifier = Modifier
                        .size(70.dp)
                        .offset { IntOffset(ositoX.toInt(), ositoY.toInt()) }
                        .clip(CircleShape)
                        .background(Color.White)
                        .clickable { onOsitoTocado() }
                )
            }
        }
    }

    fun iniciarJuego() {
        if (juegoActivo) return // evitar múltiples inicios

        // Reiniciar estados
        tiempo = 10
        puntaje = 0
        juegoActivo = true

        // Cancelar timer anterior si existía
        timer.value?.cancel()

        // Iniciar nuevo timer
        val newTimer = object : CountDownTimer(10_000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val segundosRestantes = (millisUntilFinished / 1000).toInt()
                tiempo = segundosRestantes
            }

            override fun onFinish() {
                tiempo = 0
                juegoActivo = false
                timer.value = null
                mostrarAlertaFinJuego(context, name, puntaje, navController)
            }
        }
        newTimer.start()
        timer.value = newTimer
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // Fila con nombre, puntaje y tiempo
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NombreJugador(name)
            PuntajeJugador(puntaje)
            TiempoJuego(tiempo)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Área limitada del juego (como UIView)
        TablaDeJuego(
            ancho = anchoJuego,
            alto = altoJuego,
            ositoX = posX,
            ositoY = posY,
            onOsitoTocado = {
                puntaje++

                val maxX = with(density) { (anchoJuego - 70.dp).toPx() }
                val maxY = with(density) { (altoJuego - 70.dp).toPx() }

                posX = (0..maxX.toInt()).random().toFloat()
                posY = (0..maxY.toInt()).random().toFloat()
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Fila de botones
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
             Button(
                 onClick = { iniciarJuego() },
                 modifier = Modifier
                    .height(50.dp)
                    .weight(1f)
                    .padding(horizontal = 4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Magenta,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Jugar",
                    fontSize = 18.sp
                )
            }

            Button(
                onClick = {
                    navController.navigate("top5")
                },
                modifier = Modifier
                    .height(50.dp)
                    .weight(1f)
                    .padding(horizontal = 4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Magenta,
                    contentColor = Color.White
                )
            ) {
                Text("Top 5", fontSize = 18.sp)
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

