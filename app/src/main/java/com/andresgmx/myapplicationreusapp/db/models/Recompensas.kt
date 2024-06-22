package com.andresgmx.myapplicationreusapp.db.models

class Recompensas(
    val nombre: String,
    val minPuntos: Int,
    val descripcion: String,
    val puntos: PuntosRecompensas,
) {

    fun verificarPuntos(): Boolean{
        return puntos.punto.cantidad >= minPuntos
    }

    fun restarPuntos(): Int {
        return if (verificarPuntos()) {
            minPuntos
        } else {
            0
        }
    }
}