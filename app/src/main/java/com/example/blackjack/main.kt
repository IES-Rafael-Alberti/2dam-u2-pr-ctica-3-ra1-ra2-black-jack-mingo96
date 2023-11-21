package com.example.baraja

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.baraja.clases.Baraja
import com.example.blackjack.R
import com.example.blackjack.ui.theme.BlackJackTheme

class main : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BlackJackTheme {
                pantallaBase()
            }
        }
    }
}

@Composable
fun pantallaBase(){

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

    Column (
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Image(painter = painterResource(id = if (dadaLaVuelta) idCarta else R.drawable.c53), contentDescription = "carta",modifier=Modifier.fillMaxWidth())

        Row(Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly)
        {
            Button(onClick = actualizarCarta ) {
                Text(text = "Dame carta")
            }

            Button(onClick = reiniciar ) {
                Text(text = "reiniciar")
            }
        }
    }

}