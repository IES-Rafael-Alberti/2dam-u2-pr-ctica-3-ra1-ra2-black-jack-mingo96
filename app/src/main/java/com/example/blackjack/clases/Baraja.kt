package com.example.blackjack.clases

import android.annotation.SuppressLint
import android.content.Context

/**
 * clase que gestiona la baraja y las cartas*/
class Baraja {

    companion object {

        /**contiene las cartas*/
        var listaCartas = ArrayList<Carta>()
        /**contiene la carta actual*/
        var cartaActual=Carta()
        /**crea todas las cartas y baraja
         * @param contexto sirve para obtener el [Carta.idDrawable] de las cartas*/
        fun crearBaraja(contexto : Context) {
            listaCartas.clear()
            for (palo in Palo.values())
                for ((indice, nombre) in Nombre.values().withIndex()) {
                    listaCartas.add(
                        Carta(
                            nombre,
                            palo,
                            if(indice<9)indice + 1 else 10,
                            if (nombre == Nombre.AS) 11 else indice + 1,
                            0
                        )
                    )
                }
            establecerId(contexto)
            barajar()
        }
        /**baraja las cartas*/
        fun barajar() {
            listaCartas.shuffle()
        }
        /**actualiza [cartaActual]
         * @return en caso de quedar cartas, `true`, en caso contrario, `false`*/
        fun dameCarta():Boolean {
            if (listaCartas.isEmpty()) return false
            val carta = listaCartas.last()
            listaCartas.removeLast()
            cartaActual = carta
            return true
        }

        /**establece el id de las cartas
         * @param contexto es el contexto, necesario para obtener los id de las cartas*/
        @SuppressLint("DiscouragedApi")
        fun establecerId(contexto: Context){

            for((indice, carta) in listaCartas.withIndex()){
                carta.idDrawable = contexto.resources.getIdentifier("c${indice+1}", "drawable", contexto.packageName)
            }

        }
    }
}