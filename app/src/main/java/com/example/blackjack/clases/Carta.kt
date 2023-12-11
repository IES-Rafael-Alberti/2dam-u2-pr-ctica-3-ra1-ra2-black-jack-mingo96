package com.example.blackjack.clases
/**clase que contiene los datos de las cartas*/
class Carta(nombreE:Nombre, paloE:Palo, puntosMinE:Int, puntosMaxE:Int, idDrawableE:Int) {
/**nombre de la carta*/
    val nombre=nombreE
/**palo de la carta*/
    val palo =paloE
/**puntos minimos de la carta*/
    val puntosMin =puntosMinE
/**puntos maximos de la carta (solo para cuando [nombre] sea [Nombre.AS])*/
    val puntosMax =puntosMaxE 
/**id de recurso de la carta*/
    var idDrawable =idDrawableE
/**constructor vacío de ejemplo*/
    constructor():this(Nombre.NUEVE,Palo.PICAS, 1, 1, 1)

    override fun toString(): String {
        return "$nombre de $palo"
    }
}