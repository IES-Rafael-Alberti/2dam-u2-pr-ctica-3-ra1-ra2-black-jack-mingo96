package com.example.blackjack.clases

class Jugador {

    var mano = mutableListOf<Carta>()

    var haTerminado = false

    fun recibeCarta(carta: Carta){
        mano+= carta
    }

    fun reinicio(){
        mano.clear()
    }

    fun calcularPuntuacion():Int{
        var contador = 0
        for (carta in mano){
            contador +=carta.puntosMin
        }
        return contador
    }

    fun sePasa():Boolean{
        return calcularPuntuacion()>21
    }

}