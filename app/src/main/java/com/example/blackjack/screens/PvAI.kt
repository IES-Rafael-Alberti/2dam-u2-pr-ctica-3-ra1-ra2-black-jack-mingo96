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

@Composable
fun VsIa(navController: NavHostController, jugadores:Array<Jugador>){

    var iniciado by rememberSaveable {
        mutableStateOf(true)
    }

    val contexto = LocalContext.current

    var idCarta by rememberSaveable {
        mutableStateOf(Baraja.cartaActual.idDrawable)
    }

    var dadaLaVuelta by rememberSaveable {
        mutableStateOf(false)
    }

    if (iniciado) {
        Baraja.crearBaraja(contexto)
        Baraja.barajar()
        idCarta = Baraja.cartaActual.idDrawable
        iniciado = false
    }

    var turno by rememberSaveable {
        mutableStateOf(false)
    }

    var robarCarta: () -> Boolean = {

        val esteJugador = if (!turno) jugadores[0] else jugadores[1]

        if (Baraja.dameCarta()) {
            esteJugador.recibeCarta(Baraja.cartaActual)
            true
        } else false
    }

    var manoDeEsteTurno: () -> MutableList<Carta> = {
        if (!turno) jugadores[0].mano else jugadores[1].mano
    }

    var jugadorActual: () -> Jugador = {
        if (!turno) jugadores[0] else jugadores[1]
    }
    var jugadorRival: () -> Jugador = {
        if (turno) jugadores[0] else jugadores[1]
    }

    var pasar :()->Unit={
        jugadorActual.invoke().haTerminado = true
        turno = !turno
    }

    if (jugadorActual.invoke().haTerminado) {
        turno = !turno
    }

    if (jugadores[0].haTerminado && jugadores[1].haTerminado){
        navController.navigate(Rutas.PantallaResultado.ruta)
    }

    if (!turno) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            //esto es la gestion de como se muestran las cartas
            if (manoDeEsteTurno.invoke().isEmpty()) {
                Image(
                    painter = painterResource(id = cartaBocaabajo),
                    contentDescription = "bocaAbajo",
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .fillMaxHeight(0.5f)
                        .fillMaxWidth()
                )
            } else {
                Box(
                    Modifier
                        .fillMaxHeight(0.5f)
                        .fillMaxWidth()
                        .offset(x = 40.dp)
                ) {
                    var x = (-40).dp
                    var y = 0.dp

                    for ((indice, carta) in manoDeEsteTurno.invoke().withIndex()) {
                        Image(
                            painter = painterResource(id = carta.idDrawable),
                            contentDescription = "carta ${carta.nombre} de ${carta.palo}",
                            contentScale = ContentScale.FillHeight,
                            modifier = Modifier
                                .offset(x, y)
                                .fillMaxHeight()
                        )

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

            //botones
            Row(
                Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Image(painter = painterResource(id = R.drawable.mano),
                    contentDescription = "otra carta",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(0.45f)
                        .clickable {
                            //ejecuta un robar carta, si da falso es que no quedan, mostramos un toast, cambiamos de turno
                            if (!robarCarta.invoke()) {
                                Toast
                                    .makeText(contexto, "no hay mas cartas, barajando", Toast.LENGTH_SHORT)
                                    .show()
                                Baraja.crearBaraja(contexto)
                            }

                            if (jugadorActual
                                    .invoke()
                                    .sePasa()
                            ) {
                                jugadorActual.invoke().haTerminado = true
                                Toast
                                    .makeText(
                                        contexto,
                                        "jugador ${if (turno) "2" else "1"} se ha pasado",
                                        Toast.LENGTH_SHORT
                                    )
                                    .show()
                            }


                            turno = !turno

                        })
                Image(painter = painterResource(id = R.drawable.disminucion),
                    contentDescription = "paso",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .clickable {
                            pasar()
                        })
            }

        }
    }else
    {
        if (jugadorActual.invoke().calcularPuntuacion()>15) {
            pasar
            jugadorActual.invoke().haTerminado = true
        }
        else{
            if(!jugadorActual.invoke().sePasa())robarCarta.invoke()
        }
        turno = !turno
    }

}