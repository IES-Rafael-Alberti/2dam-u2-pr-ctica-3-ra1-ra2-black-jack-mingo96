package com.example.blackjack.clases

sealed class Rutas(val ruta : String) {

    object PantallaInicio : Rutas("PantallaInicio/")
    object Pantalla1vs1 : Rutas("Pantalla1vs1/")
    object PantallavsIA : Rutas("PantallavsIA/")
    object PantallaResultado : Rutas("PantallaResultado/")
    object PantallaCambioTurno : Rutas("PantallaCambioTurno/")
}