package com.example.blackjack

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExitTransition
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.blackjack.clases.Rutas
import com.example.blackjack.screens.VsIa
import com.example.blackjack.screens.cambioTurno
import com.example.blackjack.screens.eleccionModo
import com.example.blackjack.screens.pantallapvp
import com.example.blackjack.screens.resultado
import com.example.blackjack.ui.theme.BlackJackTheme
import com.example.blackjack.viewModels.pvpViewModel

class MainActivity : ComponentActivity() {

    val pvpViewModel : pvpViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation=ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContent {
            BlackJackTheme {

                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = Rutas.PantallaInicio.ruta) {

                    composable(Rutas.PantallaInicio.ruta) {
                        eleccionModo(navController = navController)
                    }
                    
                    composable(Rutas.Pantalla1vs1.ruta,
                        exitTransition = { ExitTransition.None}){

                        pantallapvp(navController = navController,
                            viewModel = pvpViewModel
                        )
                    }


                    composable(Rutas.PantallavsIA.ruta,
                        exitTransition = { ExitTransition.None}){
                        VsIa(navController = navController, pvpViewModel)
                    }

                    composable(Rutas.PantallaCambioTurno.ruta
                    ){
                        cambioTurno(navController = navController, pvpViewModel)
                    }

                    composable(Rutas.PantallaResultado.ruta){
                        resultado(navController, pvpViewModel)
                    }
                }
            }
        }
    }
}

/*

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
        Image(painter = painterResource(id = if (dadaLaVuelta) idCarta else R.drawable.c53)
            , contentDescription = "carta"
            ,contentScale = ContentScale.Crop
            ,modifier= Modifier.fillMaxWidth())

        Row(
            Modifier.fillMaxWidth(),
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
*/
