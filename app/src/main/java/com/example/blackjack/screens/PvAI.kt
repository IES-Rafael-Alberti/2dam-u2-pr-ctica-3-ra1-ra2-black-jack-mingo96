package com.example.blackjack.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.blackjack.clases.Rutas
import com.example.blackjack.ui.theme.tableGreen
import com.example.blackjack.viewModels.PartidaViewModel

/**pantalla que ve el jugador al estar en modo vs IA*/
@Composable
fun VsIa(navController: NavHostController, controladorPartida: PartidaViewModel){


    if(controladorPartida.partidaTerminada()){
        navController.navigate(Rutas.PantallaResultado.ruta)
    }
    //anulamos backhandler
    BackHandler{}

    //si no hemos terminado y es nuestro turno, muestra la pantalla normal
    if (!controladorPartida.turnoPublico.value!! && !controladorPartida.jugadorActual().haTerminado) {
        if (controladorPartida.jugadorActual().haTerminado) controladorPartida.cambiaTurno()
        Column(
            Modifier
                .fillMaxSize()
                .background(tableGreen),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextoJugador(controladorPartida = controladorPartida)

            //esto es la gestion de como se muestran las cartas
            if (controladorPartida.manoDeEsteTurno().isEmpty()) {
                Image(
                    painter = painterResource(id = cartaBocaabajo),
                    contentDescription = "bocaAbajo",
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .fillMaxHeight(0.5f)
                        .fillMaxWidth()
                )
            } else {
                MostrarCartasConFormato(controladorPartida = controladorPartida)
            }

            Botones(controladorPartida = controladorPartida, navController = navController)

        }
    }else
    {
        if(controladorPartida.turnoPublico.value!!)controladorPartida.turnoDeIa()
    }

}