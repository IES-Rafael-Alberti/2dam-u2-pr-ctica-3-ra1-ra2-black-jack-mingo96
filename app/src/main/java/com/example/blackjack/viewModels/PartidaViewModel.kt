package com.example.blackjack.viewModels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.blackjack.clases.Baraja
import com.example.blackjack.clases.Carta
import com.example.blackjack.clases.Jugador

class PartidaViewModel(application: Application) : AndroidViewModel(application) {

    /**contexto de la app*/
    private val contexto = MutableLiveData( getApplication<Application>().applicationContext)

    /**booleano del turno, falso es jug1, falso es jug2*/
    private val turno = MutableLiveData(false)
    /**valor de turno a mostrar*/
    val turnoPublico :LiveData<Boolean> = turno
    /**datos jugador 1*/
    private val jugador1 = MutableLiveData<Jugador>()
    /**datos jugador 2*/
    private val jugador2 = MutableLiveData<Jugador>()

    /**constructor base, al ejecutarse usa [iniciar()]*/
    init {
        iniciar()
    }

    /**reinicia todos los datos y da dos cartas a cada jugador*/
    fun iniciar(){
        Baraja.crearBaraja(contexto.value!!)
        Baraja.barajar()
        jugador1.value = Jugador()
        jugador2.value = Jugador()
        for (i in 1..2){
            Baraja.dameCarta()
            jugador1.value!!.recibeCarta(Baraja.cartaActual)
            Baraja.dameCarta()
            jugador2.value!!.recibeCarta(Baraja.cartaActual)
        }

        turno.value = false
    }

    /**devuelve el jugador según [turno]*/
    fun jugadorActual(): Jugador {
        return if (turno.value!!) jugador2.value!! else jugador1.value!!
    }

    /**devuelve el jugador contrario segun [turno]*/
    private fun jugadorRival(): Jugador {
        return if (!turno.value!!) jugador2.value!! else jugador1.value!!
    }

    /**le da una carta al jugador actual
     * @return `true` en caso de que se pueda, `false` en caso de que no queden cartas*/
    private fun robarCarta():Boolean{
        if (Baraja.dameCarta()){
            jugadorActual().recibeCarta(Baraja.cartaActual)
            return true
        }
        return false
    }

    /**devuelve la mano de este turno*/
    fun manoDeEsteTurno(): MutableList<Carta> {
        return jugadorActual().mano
    }

    /**representa la acción de pasar en el jugador actual*/
    fun pasar(){
        jugadorActual().haTerminado = true
        turno.value =!turno.value!!
    }

    /**comprueba si el jugador actual ha terminado, para volver al turno del otro*/
    fun comprobarTurno(){
        if (jugadorActual().haTerminado){
            turno.value = !turno.value!!
        }
    }

    /**representa si la partida ha terminado
     * @return `true` en caso de que hayan terminado los dos, `false` en caso de que alguno no*/
    fun partidaTerminada():Boolean = jugador1.value!!.haTerminado && jugador2.value!!.haTerminado

    /**representa si el rival ha terminado
     * @return `true` si el rival ha terminado ya, `false` si el rival no ha terminado*/
    fun rivalHaTerminado():Boolean = jugadorRival().haTerminado

    fun actualHaTerminado():Boolean = jugadorActual().haTerminado

    /**intenta robar cartas, aunque no sea fisicamente posible, cubre el caso de que no con un toast
     * y comprueba si el jugador se ha pasado*/
    fun darCarta(){
        if (!robarCarta()) Toast.makeText(contexto.value,"no hay mas cartas",Toast.LENGTH_SHORT).show()
        if (jugadorActual().sePasa()) {
            jugadorActual().haTerminado = true
            Toast.makeText(contexto.value,"el jugador ${if(!turno.value!!) 1 else 2} se ha pasado", Toast.LENGTH_SHORT).show()
        }
    }

    /**cambia el [turno]*/
    fun cambiaTurno(){
        turno.value = !turno.value!!
    }

    /**devuelve la ultima carta del jugador actual*/
    fun ultimaCarta(): Carta {
        return jugadorActual().mano.last()
    }

    /**tunro de la IA, si se pasa con la siguiente carta no la pilla y pasa, muestra con toast lo que ha hecho*/
    fun turnoDeIa(){
        if(!jugadorActual().haTerminado) {
            if(jugadorActual().calcularPuntuacion()>jugadorRival().calcularPuntuacion() && jugadorRival().haTerminado) pasar()
            if (jugadorActual().calcularPuntuacion() + Baraja.listaCartas.last().puntosMin > 21) {
                pasar()
                Toast.makeText(contexto.value, "El rival ha pasado", Toast.LENGTH_SHORT).show()
            } else {
                if (!jugadorActual().haTerminado) robarCarta()
                Toast.makeText(contexto.value, "El rival ha cogido otra carta", Toast.LENGTH_SHORT).show()
            }
        }
        turno.value = !turno.value!!
    }

    /**según los datos de los jugadores, devuelve el resultado*/
    fun compararDatos(
        resultado1: Boolean = jugador1.value!!.sePasa(),
        resultado2: Boolean = jugador2.value!!.sePasa()
    ):String =
        if (resultado1 && resultado2){
            "Nadie gana"
        } else if(resultado1){
            "Gana jugador 2, jugador 1 se pasó"
        } else if (resultado2){
            "Gana jugador 1, jugador 2 se pasó"
        } else{
            val punt1 = jugador1.value!!.calcularPuntuacion()
            val punt2 = jugador2.value!!.calcularPuntuacion()
            if (punt1>punt2){
                "Gana jugador 1 por puntuacion"
            } else if (punt1<punt2){
                "Gana jugador 2 por puntuacion"
            } else "Empate por puntuaciones iguales"
        }

    /**
     * devuelve la mano dado un numero de jugador*/
    fun manojugador(jugador: Int): MutableList<Carta> {
        return if (jugador==1){
            jugador1.value!!.mano
        }else
            jugador2.value!!.mano
    }

    /**
     * devuelve el orden de jugadores segun su puntuacion, si son iguales da el 1 primero*/
    fun ordenJugadores(): Pair<Int, Int> {
        return if (mayorJugador() == 1){
            Pair(1, 2)
        } else Pair(2, 1)
    }

    private fun mayorJugador(): Int {
        return if ((jugador1.value!!.calcularPuntuacion() > jugador2.value!!.calcularPuntuacion() && !jugador1.value!!.sePasa()) || (!jugador1.value!!.sePasa() && jugador2.value!!.sePasa()))
            1 else 2
    }

}