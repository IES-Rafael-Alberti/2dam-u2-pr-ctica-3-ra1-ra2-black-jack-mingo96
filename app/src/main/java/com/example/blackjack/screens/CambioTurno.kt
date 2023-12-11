package com.example.blackjack.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.blackjack.ui.theme.tableGreen
import com.example.blackjack.viewModels.PartidaViewModel

/**escena para el intercambio de turno entre jugadores*/
@Composable
fun CambioTurno(navController: NavController, viewModel : PartidaViewModel) {

    //guarda si el boton para ocultar los datos del jugador ha sido pulsado
    var botonPulsado by rememberSaveable {
        mutableStateOf(viewModel.rivalHaTerminado())
    }

    //tiene el mismo efecto que pasar turno
    BackHandler {
        botonPulsado = true
        viewModel.cambiaTurno()
        navController.navigateUp()
    }

    Column (Modifier.fillMaxSize().background(tableGreen),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally){

        Column (verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally){
            Text(text = "Dale el móvil al otro jugador", style = TextStyle(Color.White, fontSize = 20.sp))
            //pasar turno
            Button(onClick = {
                botonPulsado = true
                viewModel.cambiaTurno()
                navController.navigateUp()
            }) {
                Text(text = "Pasar turno")
            }
        }

        //info del jugador, si el boton ha sido pulsado muestra una carta del revés y el boton de ocultar desactivado
        Column (modifier = Modifier.fillMaxHeight(0.7f), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(20.dp)){

            if (!botonPulsado) {
                Text(text = "Has sacado el ${viewModel.ultimaCarta()}", style = TextStyle(Color.White, fontSize = 20.sp))
                MostrarCarta(carta = viewModel.ultimaCarta())
                Text(text = "Llevas una puntuacion de ${viewModel.jugadorActual().calcularPuntuacion()}", style = TextStyle(Color.White, fontSize = 20.sp))
            } else{
                Image(painter = painterResource(id = cartaBocaabajo),
                    contentDescription = "carta bocaabajo",
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier.fillMaxHeight())
            }

        }
        Button(onClick = {
            botonPulsado = true
        }, enabled = !botonPulsado,
            contentPadding = PaddingValues(20.dp)) {
        Text(text = "Pulsa para ocultar")
    }
    }

}