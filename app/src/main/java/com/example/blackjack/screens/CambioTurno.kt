package com.example.blackjack.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.blackjack.viewModels.pvpViewModel

@Composable
fun cambioTurno(navController: NavController, viewModel : pvpViewModel) {



    var botonPulsado by rememberSaveable {
        mutableStateOf(false)
    }

    BackHandler {
        botonPulsado = true
        viewModel.cambiaTurno()
        navController.navigateUp()
    }

    Column (Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
        Text(text = "Pasale el movil al otro jugador")
        Button(onClick = {
            botonPulsado = true
            viewModel.cambiaTurno()
            navController.navigateUp()
        }) {
            Text(text = "Pasar turno")
        }


        Column (modifier = Modifier.fillMaxHeight(0.5f), horizontalAlignment = Alignment.CenterHorizontally){

            if (!botonPulsado) {
                Text(text = "Has sacado el ${viewModel.manoDeEsteTurno().last()}")
                mostrarCarta(carta = viewModel.manoDeEsteTurno().last())
            }

        }
        Button(onClick = {
            botonPulsado = true
        }, enabled = !botonPulsado) {
        Text(text = "Pulsa para ocultar")
    }
    }

}