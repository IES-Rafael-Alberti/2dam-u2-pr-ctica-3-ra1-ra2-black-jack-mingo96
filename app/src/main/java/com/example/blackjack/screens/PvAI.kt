package com.example.blackjack.screens

import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.blackjack.R
import com.example.blackjack.clases.Baraja
import com.example.blackjack.clases.Carta
import com.example.blackjack.clases.Jugador
import com.example.blackjack.clases.Rutas
import com.example.blackjack.viewModels.pvpViewModel

@Composable
fun VsIa(navController: NavHostController, viewModel: pvpViewModel){

    if(viewModel.partidaTerminada()){
        navController.navigate(Rutas.PantallaResultado.ruta)
    }

    if (!viewModel.turnoPublico.value!!) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

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
                mostrarCartasConFormato(viewModel = viewModel)
            }

            botonesPVP(viewModel = viewModel, navController = navController)

        }
    }else
    {
        viewModel.turnoDeIa()
    }

}