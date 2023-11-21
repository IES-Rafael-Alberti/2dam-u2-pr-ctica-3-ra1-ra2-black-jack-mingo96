package com.example.baraja.clases

class Carta(nombreE:Nombre, paloE:Palo, puntosMinE:Int, puntosMaxE:Int, idDrawableE:Int) {

    val nombre=nombreE

    val palo =paloE

    val puntosMin =puntosMinE

    val puntosMax =puntosMaxE 

    var idDrawable =idDrawableE

    constructor():this(Nombre.NUEVE,Palo.PICAS, 1, 1, 1)
}