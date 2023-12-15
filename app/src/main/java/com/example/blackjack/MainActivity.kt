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
import com.example.blackjack.screens.CambioTurno
import com.example.blackjack.screens.EleccionModo
import com.example.blackjack.screens.Pantallapvp
import com.example.blackjack.screens.Resultado
import com.example.blackjack.ui.theme.BlackJackTheme
import com.example.blackjack.viewModels.PartidaViewModel

class MainActivity : ComponentActivity() {

    private val controladorPartidaViewModel : PartidaViewModel by viewModels()

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
                        EleccionModo(navController = navController, controladorPartidaViewModel = controladorPartidaViewModel)
                    }
                    
                    composable(Rutas.Pantalla1vs1.ruta,
                        exitTransition = { ExitTransition.None}){

                        Pantallapvp(navController = navController,
                            controladorPartida = controladorPartidaViewModel
                        )
                    }


                    composable(Rutas.PantallavsIA.ruta,
                        exitTransition = { ExitTransition.None}){
                        VsIa(navController = navController, controladorPartidaViewModel)
                    }

                    composable(Rutas.PantallaCambioTurno.ruta
                    ){
                        CambioTurno(navController = navController, controladorPartidaViewModel)
                    }

                    composable(Rutas.PantallaResultado.ruta){
                        Resultado(navController, controladorPartidaViewModel)
                    }
                }
            }
        }
    }
}
