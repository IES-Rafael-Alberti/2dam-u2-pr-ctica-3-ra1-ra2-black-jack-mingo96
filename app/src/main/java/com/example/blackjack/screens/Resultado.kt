package com.example.blackjack.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
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
import com.example.blackjack.ui.theme.Brown
import com.example.blackjack.ui.theme.tableGreen

/**escena que se muestra al acabar una partida*/
@Composable
fun Resultado(navController: NavController, pvpViewModel: PartidaViewModel) {

    //el backhandler hace lo mismo que el botón de salir
    BackHandler {
        navController.navigate(Rutas.PantallaInicio.ruta)
        pvpViewModel.iniciar()
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(tableGreen),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val offset = Offset(5.0f, 10.0f)
        ResultadoTexto(offset = offset)

        //texto que explica quien ha ganado
        TextoResultado(pvpViewModel = pvpViewModel, offset = offset)

        //boton para salir
        BotonSalir(navController = navController, pvpViewModel = pvpViewModel, offset = offset)

    }
}

@Composable
private fun ResultadoTexto(offset: Offset){

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
}

/**el texto del resultado
 * @param offset es el offset para la sombra*/
@Composable
private fun TextoResultado(pvpViewModel: PartidaViewModel, offset: Offset){

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
}

/**boton para salir
 * @param offset es el offset para la sombra*/
@Composable
private fun BotonSalir(navController: NavController, pvpViewModel:PartidaViewModel, offset:Offset){
    ElevatedButton(onClick = {
        navController.navigate(Rutas.PantallaInicio.ruta)
        pvpViewModel.iniciar()
    },
        elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 20.dp, pressedElevation = 2.dp),
        modifier = Modifier.fillMaxWidth(0.8f),
        colors = ButtonDefaults.buttonColors(containerColor = Brown)) {
        Text(text = "Pulsa para volver al menú principal",
            style = TextStyle(
                fontSize = 30.sp,
                lineHeight = 35.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                shadow = Shadow(
                    color = Color.Black,
                    offset = offset,
                    blurRadius = 3f
                )),
            modifier = Modifier.padding(10.dp))
    }
}
