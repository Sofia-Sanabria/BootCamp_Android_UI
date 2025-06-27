package com.example.app_games_android.ui.poker.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.ui.poker.ui.Card
import com.example.ui.poker.ui.PokerHand
import com.example.ui.poker.ui.dealHands
import com.example.ui.poker.ui.compareHands

@Composable
fun PokerScreen(nombreJugador: String) {
    // guarda el resultado
    var resultado by remember { mutableStateOf("") }

    // guarda las manos
    var manoJugador by remember { mutableStateOf<PokerHand?>(null) }
    var manoCPU by remember { mutableStateOf<PokerHand?>(null) }

    // colores para mostrar ganador
    var colorJugador by remember { mutableStateOf(Color.Transparent) }
    var colorCPU by remember { mutableStateOf(Color.Transparent) }

    // primer click para cambiar texto del boton
    var primerClick by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2E7D32)) // fondo verde estilo mesa
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "POKER GAME",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val (j1, cpu) = dealHands()
                manoJugador = j1
                manoCPU = cpu
                resultado = compareHands(j1, cpu)

                val ganaJ1 = resultado.contains("Mano 1")
                val ganaCPURes = resultado.contains("Mano 2")

                colorJugador = if (ganaJ1) Color.Green else Color.Red
                colorCPU = if (ganaCPURes) Color.Green else Color.Red

                primerClick = false
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0))
        ) {
            Text(
                text = if (primerClick) "🎮 jugar" else "🔁 volver a repartir",
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (manoJugador != null && manoCPU != null) {
            // muestra nombre del jugador logueado
            Text(
                text = "Cecilia",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .background(colorJugador)
                    .padding(12.dp)
            ) {
                ManoDeCartas(manoJugador!!.cards)
            }

            Text(
                text = "CPU",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .background(colorCPU)
                    .padding(12.dp)
            ) {
                ManoDeCartas(manoCPU!!.cards)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = resultado,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
        }
    }
}

@Composable
fun ManoDeCartas(cartas: List<Card>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        cartas.forEach { carta ->
            CartaPoker(
                valor = carta.value,
                palo = when (carta.suit.symbol) {
                    'S' -> "♠️"
                    'C' -> "♣️"
                    'H' -> "♥️"
                    'D' -> "♦️"
                    else -> "?"
                }
            )
        }
    }
}

@Composable
fun CartaPoker(valor: String, palo: String, modifier: Modifier = Modifier) {
    val esRojo = palo == "♥️" || palo == "♦️"
    val colorTexto = if (esRojo) Color.Red else Color.Black

    Box(
        modifier = modifier
            .size(width = 60.dp, height = 80.dp)
            .padding(4.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .border(2.dp, Color.DarkGray, shape = RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = valor, color = colorTexto, style = MaterialTheme.typography.bodyLarge)
            Text(text = palo, color = colorTexto, style = MaterialTheme.typography.bodyLarge)
        }
    }
}
