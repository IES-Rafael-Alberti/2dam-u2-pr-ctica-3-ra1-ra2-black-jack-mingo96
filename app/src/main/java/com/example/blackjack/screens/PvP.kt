package com.example.blackjack.screens

import androidx.activity.compose.BackHandler
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
import com.example.blackjack.ui.theme.tableGreen
import com.example.blackjack.viewModels.PartidaViewModel

val cartaBocaabajo = R.drawable.c53

@Composable
fun Pantallapvp(navController: NavHostController, viewModel: PartidaViewModel) {

    BackHandler{}

    viewModel.comprobarTurno()

    if (viewModel.partidaTerminada()){
        navController.navigate(Rutas.PantallaResultado.ruta)
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(tableGreen),
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
