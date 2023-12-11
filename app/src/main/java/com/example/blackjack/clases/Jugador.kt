package com.example.blackjack.clases

/**contiene toda la informacion del jugador*/
class Jugador {
/**cartas actuales del jugador*/
    var mano = mutableListOf<Carta>()
/**representa si el jugador ha terminado ya esta partida*/
    var haTerminado = false
    /**funcion para añadir carta a la mano
     * @param carta carta a añadir*/
    fun recibeCarta(carta: Carta){
        mano+= carta
    }
    /**vacía la mano*/
    fun reinicio(){
        mano.clear()
    }
    /**calcula la puntuacion actual del jugador
     * @return la puntuacion actual del jugador*/
    fun calcularPuntuacion():Int{
        var contador = 0
        for (carta in mano){
            contador +=carta.puntosMin
        }
        return contador
    }
    /**calcula si el jugador se pasa de 21
     * @return `true` si se pasa, `false` si no se pasa*/
    fun sePasa():Boolean{
        return calcularPuntuacion()>21
    }

}