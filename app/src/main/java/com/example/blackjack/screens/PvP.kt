package com.example.blackjack.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.navigation.NavHostController
import com.example.blackjack.R
import com.example.blackjack.clases.Carta
import com.example.blackjack.clases.Rutas
import com.example.blackjack.viewModels.PartidaViewModel

val cartaBocaabajo = R.drawable.c53

@Composable
fun Pantallapvp(navController: NavHostController, viewModel: PartidaViewModel) {

    viewModel.comprobarTurno()

    if (viewModel.partidaTerminada()){
        navController.navigate(Rutas.PantallaResultado.ruta)
    }

    Column(
        Modifier.fillMaxSize().background(Color(29, 110, 0)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        
        TextoJugador(viewModel = viewModel)

        //esto es la gestion de como se muestran las cartas
        if (viewModel.manoDeEsteTurno().isEmpty()) {
            Image(painter = painterResource(id = cartaBocaabajo),
                contentDescription = "bocaAbajo",
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .fillMaxHeight(0.5f)
                    .fillMaxWidth()
            )
        } else {
            MostrarCartasConFormato(viewModel)
        }

        Botones(viewModel = viewModel, navController = navController)

    }

}

@Composable
fun TextoJugador(viewModel: PartidaViewModel){
    Text(text = "Turno del jugador ${if (viewModel.turnoPublico.value!!) "2" else "1"}" +
            if(viewModel.rivalHaTerminado()) ", el jugador contrario ya terminó" else "",
        textAlign = TextAlign.Center, style = TextStyle(Color.White, fontSize = 20.sp)
    )
}

@Composable
fun MostrarCartasConFormato(viewModel: PartidaViewModel){
    Box(
        Modifier
            .fillMaxHeight(0.5f)
            .fillMaxWidth()
            .offset(x = 40.dp)
    ) {
        var x = (-40).dp
        var y = 0.dp

        for ((indice, carta) in viewModel.manoDeEsteTurno().withIndex()) {
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

@Composable
fun MostrarCarta(carta: Carta, x: Dp = 0.dp, y:Dp = 0.dp){
    Image(
        painter = painterResource(id = carta.idDrawable),
        contentDescription = "carta ${carta.nombre} de ${carta.palo}",
        contentScale = ContentScale.FillHeight,
        modifier = Modifier
            .offset(x, y)
            .fillMaxHeight(0.9f)
    )
}

@Composable
fun Botones(viewModel: PartidaViewModel, navController:NavController){
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

                    viewModel.darCarta()
                    navController.navigate(Rutas.PantallaCambioTurno.ruta)

                })
        Image(painter = painterResource(id = R.drawable.disminucion),
            contentDescription = "paso",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .clickable {
                    viewModel.pasar()
                    navController.navigate(Rutas.PantallaCambioTurno.ruta)
                })
    }
}