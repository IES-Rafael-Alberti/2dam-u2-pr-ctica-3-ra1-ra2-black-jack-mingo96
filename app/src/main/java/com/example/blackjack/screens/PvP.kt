package com.example.blackjack.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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

val cartaBocaabajo = R.drawable.c53

@Composable
fun pantallapvp(navController: NavHostController, jugadores:Array<Jugador>) {

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

    var jugador1 =jugadores[0]
    var jugador2= jugadores[1]

    var robarCarta: () -> Boolean = {

        val esteJugador = if (!turno) jugador1 else jugador2

        if (Baraja.dameCarta()) {
            esteJugador.recibeCarta(Baraja.cartaActual)
            true
        } else false
    }

    var manoDeEsteTurno: () -> MutableList<Carta> = {
        if (!turno) jugador1.mano else jugador2.mano
    }

    var jugadorActual: () -> Jugador = {
        if (!turno) jugador1 else jugador2
    }
    var jugadorRival: () -> Jugador = {
        if (turno) jugador1 else jugador2
    }

    var pasar :()->Unit={
        jugadorActual.invoke().haTerminado = true
        turno = !turno
    }

    if (jugadorActual.invoke().haTerminado) {
        turno = !turno
    }

    if (jugador1.haTerminado && jugador2.haTerminado){
        navController.navigate(Rutas.PantallaResultado.ruta)
    }

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "turno del jugador ${if (turno) "2" else "1"}" +
                if(jugadorRival.invoke().haTerminado) ", el jugador contrario ya terminó" else "",
            textAlign = TextAlign.Center)

        //esto es la gestion de como se muestran las cartas
        if (manoDeEsteTurno.invoke().isEmpty()) {
            Image(painter = painterResource(id = cartaBocaabajo),
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
            ) {

                LazyRow(){
                    items(jugadorActual.invoke().mano){carta ->
                        imagenCarta(carta = carta)
                    }
                }
            }
        }

        //botones
        Row(
            Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Image(painter = painterResource(id = R.drawable.mano),
                contentDescription ="otra carta",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(0.45f)
                    .clickable {
                        //ejecuta un robar carta, si da falso es que no quedan, mostramos un toast, cambiamos de turno
                        if (!robarCarta.invoke()) {
                            Toast
                                .makeText(contexto, "no hay mas cartas", Toast.LENGTH_SHORT)
                                .show()
                        }

                        Toast
                            .makeText(
                                contexto,
                                "has sacado el ${Baraja.cartaActual.nombre} de ${Baraja.cartaActual.palo}",
                                Toast.LENGTH_SHORT
                            )
                            .show()

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



                        if (!jugadorRival.invoke().haTerminado) navController.navigate(Rutas.PantallaCambioTurno.ruta)

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
                        navController.navigate(Rutas.PantallaCambioTurno.ruta)
                    })
        }

    }

}

@Composable
fun imagenCarta(carta: Carta){
    Image(
        painter = painterResource(id = carta.idDrawable),
        contentDescription = "carta ${carta.nombre} de ${carta.palo}",
        contentScale = ContentScale.FillHeight,
        modifier = Modifier
            .fillMaxHeight()
    )
}