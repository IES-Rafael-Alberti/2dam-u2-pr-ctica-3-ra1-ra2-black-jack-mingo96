package com.example.blackjack.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.blackjack.R
import com.example.blackjack.clases.Carta
import com.example.blackjack.clases.Rutas
import com.example.blackjack.viewModels.PartidaViewModel

//este archivo contiene funciones que son comunes para pvp y pvAI
/**genera un texto dado el viewmodel de la partida, texto superior que indica el jugador actual,
 * y si ha terminado el otro, lo indica*/
@Composable
fun TextoJugador(controladorPartida: PartidaViewModel){
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
        Text(text = "Turno del jugador ${if (controladorPartida.turnoPublico.value!!) "2" else "1"}" +
                if(controladorPartida.rivalHaTerminado()) ", el jugador contrario ya terminó" else "",
            textAlign = TextAlign.Center, style = TextStyle(Color.White, fontSize = 20.sp)
        )
        Text(text = "Tu puntuacion actual es de ${controladorPartida.jugadorActual().calcularPuntuacion()} puntos",
            textAlign = TextAlign.Center, style = TextStyle(Color.White, fontSize = 20.sp)
        )
    }
}

/**muestra las cartas del [PartidaViewModel.jugadorActual] con un formato en el cual las cartas se
 * solapan mostrando el puntaje de la anterior*/
@Composable
fun MostrarCartasConFormato(controladorPartida: PartidaViewModel){
    Box(
        Modifier
            .fillMaxHeight(0.5f)
            .fillMaxWidth()
            .offset(x = 40.dp)
    ) {
        var x = (-40).dp
        var y = 0.dp

        for ((indice, carta) in controladorPartida.manoDeEsteTurno().withIndex()) {
            MostrarCarta(carta = carta, x = x, y = y)

            //en caso improvable de que hayan mas de 9 cartas en pantalla, las mostramos un poquito abajo
            if (indice >= 9) {
                y = 50.dp
                x = (40 * (indice - 10)).dp
            } else {
                y = 0.dp
                x = (40 * indice).dp
            }
        }
    }
}

/**muestra una carta con unos valores de desplazamiento
 * @param carta carta a mostrar
 * @param x desplazamiento horizontal, de base es 0, no hace falta rellenarlo
 * @param y desplazamiento vertical, de base es 0, no hace falta rellenarlo*/
@Composable
fun MostrarCarta(carta: Carta, x: Dp = 0.dp, y: Dp = 0.dp){
    Image(
        painter = painterResource(id = carta.idDrawable),
        contentDescription = "carta ${carta.nombre} de ${carta.palo}",
        contentScale = ContentScale.FillHeight,
        modifier = Modifier
            .offset(x, y)
            .fillMaxHeight(0.9f)
    )
}

/**muestra los botones con los que interactúa el jugador actual*/
@Composable
fun Botones(controladorPartida: PartidaViewModel, navController: NavController){
    Row(
        Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Image(painter = painterResource(id = R.drawable.mano),
            contentDescription ="otra carta",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(0.45f)
                .clickable {

                    controladorPartida.darCarta()
                    navController.navigate(Rutas.PantallaCambioTurno.ruta)

                })
        Image(painter = painterResource(id = R.drawable.disminucion),
            contentDescription = "paso",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .clickable {
                    controladorPartida.pasar()
                    navController.navigate(Rutas.PantallaCambioTurno.ruta)
                })
    }
}