package com.example.blackjack.viewModels

import android.annotation.SuppressLint
import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.blackjack.clases.Baraja
import com.example.blackjack.clases.Carta
import com.example.blackjack.clases.Jugador

@SuppressLint("StaticFieldLeak")
class pvpViewModel(application: Application) : AndroidViewModel(application) {

    private val contexto = getApplication<Application>().applicationContext

    private val turno = MutableLiveData<Boolean>(false)
    val turnoPublico :LiveData<Boolean> = turno

    private val jugador1 = MutableLiveData<Jugador>()
    private val jugador2 = MutableLiveData<Jugador>()

    init {
        iniciar()
    }
    fun iniciar(){
        Baraja.crearBaraja(contexto)
        Baraja.barajar()
        jugador1.value = Jugador()
        jugador2.value = Jugador()
        turno.value = false
    }

    fun jugadorActual(): Jugador {
        return if (turno.value!!) jugador2.value!! else jugador1.value!!
    }
    private fun jugadorRival(): Jugador {
        return if (!turno.value!!) jugador2.value!! else jugador1.value!!
    }




    fun robarCarta():Boolean{
        if (Baraja.dameCarta()){
            jugadorActual().recibeCarta(Baraja.cartaActual)
            return true
        }
        return false
    }

    fun manoDeEsteTurno(): MutableList<Carta> {
        return jugadorActual().mano
    }

    fun pasar(){
        jugadorActual().haTerminado = true
        turno.value =!turno.value!!
    }

    fun comprobarTurno(){
        if (jugadorActual().haTerminado){
            turno.value = !turno.value!!
        }
    }

    fun partidaTerminada():Boolean = jugador1.value!!.haTerminado && jugador2.value!!.haTerminado

    fun rivalHaTerminado():Boolean = jugadorRival().haTerminado

    fun darCarta(){
        if (!robarCarta()) Toast.makeText(contexto,"no hay mas cartas",Toast.LENGTH_SHORT).show()
        if (jugadorActual().sePasa()) {
            jugadorActual().haTerminado = true
            Toast.makeText(contexto,"el jugador ${if(!turno.value!!) 1 else 2} se ha pasado", Toast.LENGTH_SHORT).show()
        }
    }

    fun cambiaTurno(){
        turno.value = !turno.value!!
    }

    fun ultimaCarta(): Carta {
        return jugadorActual().mano.last()
    }
}