package com.example.blackjack.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.navArgument
import com.example.blackjack.clases.Jugador
import com.example.blackjack.clases.Rutas
import kotlin.time.Duration.Companion.seconds

@Composable
fun cambioTurno(navController: NavController, turno : Boolean) {


    Column (Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
        Text(text = "pasale el movil al otro jugador")
        Button(onClick = {navController.navigate(Rutas.Pantalla1vs1.ruta){ navArgument("turno"){!turno} } }) {
            Text(text = "pasar turno")
        }
    }

}