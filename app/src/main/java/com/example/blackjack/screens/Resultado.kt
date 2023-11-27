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

@Composable
fun resultado(navController: NavController, jugadores : Array<Jugador>){
    Column (Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){

        Text(text = "el resultado es = ${compararDatos(jugadores)}")
        
        Button(onClick = { navController.navigate(Rutas.PantallaInicio.ruta) }) {
            Text(text = "pulsa para volver al menú principal")
        }

    }
}

fun compararDatos(jugadores: Array<Jugador>):String{
    var resultado1 = jugadores[0].sePasa()
    var resultado2 = jugadores[1].sePasa()

    return if (resultado1 && resultado2){
        "nadie gana"
    } else if(resultado1){
        "gana jugador 2, jugador 1 se pasó"
    } else if (resultado2){
        "gana jugador 1, jugador 2 se pasó"
    } else{
        if (jugadores[0].calcularPuntuacion()>jugadores[1].calcularPuntuacion()){
            "gana jugador 1 por puntuacion"
        } else if (jugadores[0].calcularPuntuacion()<jugadores[1].calcularPuntuacion()){
            "gana jugador 2 por puntuacion"
        } else "empate por puntuaciones iguales"
    }
}