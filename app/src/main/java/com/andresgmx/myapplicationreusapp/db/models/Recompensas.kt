package com.andresgmx.myapplicationreusapp.db.models

class Recompensas(
    val id: Long? = null,
    val nombre: String,
    val minPuntos: Int,
    val descripcion: String,
    val puntos: MutableList<PuntosRecompensas> = mutableListOf(),
) {
    override fun toString(): String {
        return "Recompensas(nombre='$nombre', minPuntos=$minPuntos, descripcion='$descripcion', puntos=$puntos)"
    }
    /*fun agregarPuntos(puntos: Puntos) {
        val puntosRecompensas = PuntosRecompensas(this, puntos)
        puntos.add(puntosRecompensas)
    */

}