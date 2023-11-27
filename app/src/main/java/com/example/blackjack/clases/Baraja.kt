package com.example.blackjack.clases

import android.content.Context

class Baraja {

    companion object {


        var listaCartas = ArrayList<Carta>()

        var cartaActual=Carta()

        fun crearBaraja(contexto : Context) {
            listaCartas.clear()
            for (palo in Palo.values())
                for ((indice, nombre) in Nombre.values().withIndex()) {
                    listaCartas.add(
                        Carta(
                            nombre,
                            palo,
                            indice + 1,
                            if (nombre == Nombre.AS) 11 else indice + 1,
                            0
                        )
                    )
                }
            establecerId(contexto)
            barajar()
        }

        fun barajar() {
            listaCartas.shuffle()
        }

        fun dameCarta():Boolean {
            if (listaCartas.isEmpty()) return false
            val carta = listaCartas.last()
            listaCartas.removeLast()
            cartaActual = carta
            return true
        }

        fun establecerId(contexto: Context){

            for((indice, carta) in listaCartas.withIndex()){
                carta.idDrawable = contexto.resources.getIdentifier("c${indice+1}", "drawable", contexto.packageName)
            }

        }
    }
}