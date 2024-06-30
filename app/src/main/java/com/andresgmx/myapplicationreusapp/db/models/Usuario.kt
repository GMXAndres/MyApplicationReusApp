package com.andresgmx.myapplicationreusapp.db.models;
import com.andresgmx.myapplicationreusapp.db.models.enums.TipoMaterial;
import java.time.LocalDate

import java.util.Date

class Usuario (
    var id: Long? = null,
    var nombre: String? = null,
    var apellido: String? = null,
    var cedula: String?=null,
    var telefono: String? = null,
    var fechaNacimiento: LocalDate? = null,
    var fechaRegistro: LocalDate = LocalDate.now(),
    var cuenta: Cuenta? = null,
    var direccion: Direccion?=null,
    var puntos: Puntos? = null,
    var reciclajes: MutableList<Reciclaje> = mutableListOf(),
) {

    override fun toString(): String {
        return "Usuario(nombre=$nombre, apellido=$apellido)"
    }
    fun agregarReciclaje(reciclaje: Reciclaje) {
        if (reciclaje.usuario == this) {
            reciclajes.add(reciclaje)
        } else {
            throw IllegalArgumentException("El reciclaje no pertenece a este usuario.")
        }
    }

    fun obtenerReciclajes(): MutableList<Reciclaje> {
        return reciclajes
    }


    fun calcularPesoTotalPorMaterial(reciclajes: MutableList<Reciclaje>): Pair<Double, Map<TipoMaterial, Double>> {
        val pesoPorMaterial: MutableMap<TipoMaterial, Double> = mutableMapOf()
        var pesoTotal = 0.0
        for (reciclaje in reciclajes) {
            val pesoExistente = pesoPorMaterial.getOrDefault(reciclaje.material, 0.0)
            pesoPorMaterial[reciclaje.material] = pesoExistente + reciclaje.peso
            pesoTotal += reciclaje.peso
        }
        return Pair(pesoTotal, pesoPorMaterial)
    }
}
