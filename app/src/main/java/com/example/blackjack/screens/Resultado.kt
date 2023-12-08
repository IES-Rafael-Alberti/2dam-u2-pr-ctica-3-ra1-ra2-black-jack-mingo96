package com.example.blackjack.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.blackjack.clases.Rutas
import com.example.blackjack.viewModels.PartidaViewModel

@Composable
fun Resultado(navController: NavController, pvpViewModel: PartidaViewModel) {
    Column(
        Modifier.fillMaxSize().background(Color(29, 110, 0)),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val offset = Offset(5.0f, 10.0f)
        Text(
            text = "Resultado",
            style = TextStyle(
                fontSize = 70.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Yellow,
                shadow = Shadow(
                    color = Color(211, 84, 0),
                    offset = offset,
                    blurRadius = 3f
                )
            ),
            modifier = Modifier
                .border(5.dp, Color.Black, shape = RoundedCornerShape(10.dp))
                .padding(horizontal = 10.dp)
        )

        Text(text = pvpViewModel.compararDatos(),
            style = TextStyle(
                fontSize = 40.sp,
                lineHeight = 50.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                shadow = Shadow(
                    color = Color.Black,
                    offset = offset,
                    blurRadius = 3f
                )),
            modifier = Modifier.padding(10.dp)
        )

        Button(onClick = {
            navController.navigate(Rutas.PantallaInicio.ruta)
            pvpViewModel.iniciar()
        },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Green)) {
            Text(text = "Pulsa para volver al menú principal",
                style = TextStyle(
                    fontSize = 40.sp,
                    lineHeight = 50.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(10.dp),)
        }

    }
}
