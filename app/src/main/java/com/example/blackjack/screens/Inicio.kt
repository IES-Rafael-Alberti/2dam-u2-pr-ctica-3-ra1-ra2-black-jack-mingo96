package com.example.blackjack.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.blackjack.R
import com.example.blackjack.clases.Rutas


@Composable
fun eleccionModo(navController: NavHostController){
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally) {

        Image(painter = painterResource(id = R.drawable.pvp),
            contentDescription = "img1v1",
            Modifier.clickable { navController.navigate(Rutas.Pantalla1vs1.ruta)}
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.FillWidth)

        Image(painter = painterResource(id = R.drawable.solo),
            contentDescription = "img1v1",
            Modifier.clickable { navController.navigate(Rutas.PantallavsIA.ruta) }
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.FillWidth)

    }
}
