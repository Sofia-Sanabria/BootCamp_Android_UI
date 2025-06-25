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
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier.fillMaxSize()) {
        Tittle(Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.padding(16.dp))
        NombreJugador(Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.padding(24.dp))
//        HeaderImage(Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.padding(24.dp))
        JugarButton(onClick = {  })
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            PuntajeButton()
            AyudaButton()
        }
    }
}

@Composable
fun Tittle(modifier: Modifier) {
    Text(
        text = "BIENVENIDA",
        modifier = modifier,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFFFF9800)
    )
}

@Composable
fun NombreJugador(modifier: Modifier) {
    // val nombre: String by viewModel.nombre.observeAsState(initial = "")
    Text(
        text = "Sofia",
        modifier = modifier,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFFFF9800)
    )
}

//@Composable
//fun HeaderImage(modifier: Modifier = Modifier) {
//    Image(
//        painter = painterResource(id = R.drawable.game_logo ),
//        contentDescription = "Header",
//        modifier = modifier
//    )
//}
//
//@Composable
//fun HorizontalPagerGames(){
//    val pagerState = rememberPagerState( 0 , pageCount ={ 3 } )
//    Column(horizontalAlignment = Alignment.CenterHorizontally) {
//        HorizontalPager(
//            state = pagerState,
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(200.dp)
//        ) { page ->
//            Box(
//                modifier = Modifier
//                    .fillMaxSize(),
//                contentAlignment = Alignment.Center
//            ) {
//                when (page) {
//                    0 -> HeaderImage(
//                        Modifier
//                            .fillMaxSize()
//                            .clip(RoundedCornerShape(8.dp))
//                    )
//
//                    1 -> GenerateImage(
//                        Modifier
//                            .clip(CircleShape)
//                            .fillMaxHeight(),
//                        R.drawable.poker,
//                        ContentScale.Crop
//                    )
//
//                    2 -> GenerateImage(
//                        Modifier
//                            .clip(CircleShape)
//                            .fillMaxHeight(),
//                        R.drawable.tocame,
//                        ContentScale.Crop
//                    )
//                }
//            }
//        }
//        Spacer(modifier = Modifier.height(40.dp))

        // Solo mostrar el botón "Jugar" si la página es 1 o 2 (no mostrarlo en portada)
//        if (pagerState.currentPage != 0) {
//            JugarButton (onClick = {
//                when (pagerState.currentPage) {
//                    1 -> navController.navigate(Routes.POKER)
//                    2 -> navController.navigate(Routes.TOCAME)
//                }
//            })
//        }
//    }
//}

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
fun AyudaButton() {
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
            text = "Ayuda",
            fontSize = 20.sp,
        )
    }
}