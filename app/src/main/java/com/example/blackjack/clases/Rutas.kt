package com.example.blackjack.clases

sealed class Rutas(val ruta : String) {

    object PantallaInicio : Rutas("PantallaInicio")
    object Pantalla1vs1 : Rutas("Pantalla1vs1")
    object PantallavsIA : Rutas("PantallavsIA")
    object pantalla4 : Rutas("pantalla4"){
        fun createRoute(numerito : Int)="pantalla4/$numerito"
    }
    object pantalla5 : Rutas("pantalla5"){
        fun createRoute(nombre : String)="pantalla5?/nombre=$nombre"
    }
}