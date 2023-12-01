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
import com.example.blackjack.viewModels.pvpViewModel

val cartaBocaabajo = R.drawable.c53

@Composable
fun pantallapvp(navController: NavHostController, viewModel: pvpViewModel) {

    viewModel.comprobarTurno()

    if (viewModel.partidaTerminada()){
        navController.navigate(Rutas.PantallaResultado.ruta)
    }

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "turno del jugador ${if (viewModel.turnoPublico.value!!) "2" else "1"}" +
                if(viewModel.rivalHaTerminado()) ", el jugador contrario ya terminó" else "",
            textAlign = TextAlign.Center)

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
            Box(
                Modifier
                    .fillMaxHeight(0.5f)
                    .fillMaxWidth()
                    .offset(x = 40.dp)
            ) {
                var x = (-40).dp
                var y = 0.dp

                for ((indice, carta) in viewModel.manoDeEsteTurno().withIndex()) {
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
                contentDescription ="otra carta",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(0.45f)
                    .clickable {

                        viewModel.darCarta()
                        navController.navigate(Rutas.PantallaCambioTurno.ruta)

                    })
            Image(painter = painterResource(id = R.drawable.disminucion),
                contentDescription = "paso",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .clickable {
                    viewModel.pasar()
                    navController.navigate(Rutas.PantallaCambioTurno.ruta)
                })
        }

    }

}