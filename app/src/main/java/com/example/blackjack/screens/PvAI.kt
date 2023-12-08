package com.example.blackjack.screens

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.blackjack.clases.Rutas
import com.example.blackjack.viewModels.PartidaViewModel

@Composable
fun VsIa(navController: NavHostController, viewModel: PartidaViewModel){

    if(viewModel.partidaTerminada()){
        navController.navigate(Rutas.PantallaResultado.ruta)
    }



    if (!viewModel.turnoPublico.value!!) {
        if (viewModel.jugadorActual().haTerminado) viewModel.cambiaTurno()
        Column(
            Modifier
                .fillMaxSize()
                .background(Color(29, 110, 0)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextoJugador(viewModel = viewModel)

            //esto es la gestion de como se muestran las cartas
            if (viewModel.manoDeEsteTurno().isEmpty()) {
                Image(
                    painter = painterResource(id = cartaBocaabajo),
                    contentDescription = "bocaAbajo",
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .fillMaxHeight(0.5f)
                        .fillMaxWidth()
                )
            } else {
                MostrarCartasConFormato(viewModel = viewModel)
            }

            Botones(viewModel = viewModel, navController = navController)

        }
    }else
    {
        viewModel.turnoDeIa()
    }

}