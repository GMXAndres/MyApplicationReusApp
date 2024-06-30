package com.andresgmx.myapplicationreusapp.db.models

class PuntosRecompensas(
    val id: Long? = null,
    val punto: Puntos,
    val recompensa: Recompensas
) {
    override fun toString(): String {
        //return "PuntosRecompensas(punto=$punto, recompensa=$recompensa)"
        return "PuntosRecompensas(punto=${punto.codigo}, recompensa=[nombre=${recompensa.nombre}, min=${recompensa.minPuntos}])"
    }

}