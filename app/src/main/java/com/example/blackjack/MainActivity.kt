package com.example.blackjack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.blackjack.clases.Rutas
import com.example.blackjack.screens.eleccionModo
import com.example.blackjack.screens.pantallapvp
import com.example.blackjack.ui.theme.BlackJackTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BlackJackTheme {

                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = Rutas.PantallaInicio.ruta) {

                    composable(Rutas.PantallaInicio.ruta) {
                        eleccionModo(navController = navController)
                    }
                    
                    composable(Rutas.Pantalla1vs1.ruta){
                        pantallapvp(navController = navController)
                    }

                    composable(Rutas.PantallavsIA.ruta){

                    }
                    
                    /*
                    composable(
                        Roots.pantalla4.route,
                        arguments = listOf(
                            navArgument("numerito")
                            { type = NavType.IntType }
                        )
                    )
                    { backStackEntry ->
                        Screen4(
                            navController = navController,
                            numerito = backStackEntry.arguments?.getInt("numerito") ?: 0
                        )

                    }

                    composable(
                        Roots.pantalla4.route,
                        arguments = listOf(
                            navArgument("nombre")
                            { type = NavType.StringType }
                        )
                    )
                    { backStackEntry ->
                        Screen5(
                            navController = navController,
                            nombre = backStackEntry.arguments?.getString("nombre").orEmpty()
                        )

                    }

                     */

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
