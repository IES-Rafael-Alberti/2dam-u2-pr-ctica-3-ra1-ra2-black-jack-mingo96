package com.example.blackjack.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.blackjack.clases.Jugador
import com.example.blackjack.clases.Rutas
import com.example.blackjack.viewModels.pvpViewModel

@Composable
fun resultado(navController: NavController, pvpViewModel: pvpViewModel){
    Column (Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){

        Text(text = "el resultado es = ${pvpViewModel.compararDatos()}")
        
        Button(onClick = {
            navController.navigate(Rutas.PantallaInicio.ruta)
            pvpViewModel.iniciar()
        }) {
            Text(text = "pulsa para volver al menú principal")
        }

    }
}
