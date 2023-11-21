package com.example.blackjack.screens

import android.widget.Toast
import androidx.compose.animation.core.estimateAnimationDurationMillis
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.blackjack.R
import com.example.blackjack.clases.Baraja
import com.example.blackjack.clases.Carta
import com.example.blackjack.clases.Jugador
import kotlin.coroutines.coroutineContext

val cartaBocaabajo = R.drawable.c53

@Composable
fun pantallapvp(navController: NavHostController) {

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

    if(iniciado){
        Baraja.crearBaraja(contexto)
        Baraja.barajar()
        idCarta = Baraja.cartaActual.idDrawable
        iniciado = false
    }

    val actualizarCarta = {
        if(dadaLaVuelta){
            if(!Baraja.dameCarta()) Toast.makeText(contexto, "No quedan mas cartas!!", Toast.LENGTH_SHORT).show()
            idCarta = Baraja.cartaActual.idDrawable
        }else
            dadaLaVuelta= true
    }

    val reiniciar = {
        Baraja.crearBaraja(contexto)
        idCarta = Baraja.cartaActual.idDrawable
        dadaLaVuelta = false
    }



    var turno by rememberSaveable {
        mutableStateOf(1)
    }

    var jugador1 by remember {
        mutableStateOf(Jugador())
    }
    var jugador2 by remember {
        mutableStateOf(Jugador())
    }

    var robarCarta: () -> Boolean = {

        val esteJugador = if (turno == 1) jugador1 else jugador2

        if (Baraja.dameCarta()) {
            esteJugador.recibeCarta(Baraja.cartaActual)
            true
        } else false
    }

    var manoDeEsteTurno: () -> MutableList<Carta> = {
        if (turno == 1) jugador1.mano else jugador2.mano
    }

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "turno del jugador $turno")
        if (manoDeEsteTurno.invoke().isEmpty()) {
            Image(painter = painterResource(id = cartaBocaabajo), contentDescription = "bocaAbajo")
        } else {
            Row(
                Modifier.fillMaxWidth(0.7f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                for (carta in manoDeEsteTurno.invoke()) {
                    Image(
                        painter = painterResource(id = carta.idDrawable),
                        contentDescription = "carta ${carta.nombre} de ${carta.palo}"
                    )
                }
            }
        }
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                if (!robarCarta.invoke()) {
                    Toast.makeText(navController.context, "no hay mas cartas", Toast.LENGTH_SHORT)
                        .show()
                }
                turno = if (turno == 1) 2 else 1
            }
            ) {
                Text(text = "dame carta")
            }
            Button(onClick = { }) {
                Text(text = "paso")
            }
        }
    }

}