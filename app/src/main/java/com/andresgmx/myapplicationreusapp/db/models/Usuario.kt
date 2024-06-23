package com.andresgmx.myapplicationreusapp.db.models;
import com.andresgmx.myapplicationreusapp.db.models.enums.TipoMaterial;
import java.time.LocalDate

import java.util.Date

class Usuario (
    var nombre: String,
    var apellido: String,
    var cedula: String,
    var telefono: String,
    var fechaNacimiento: LocalDate,
    var fechaRegistro: LocalDate,
    var cuenta: Cuenta? = null,
    var direccion: Direccion?=null,
    var puntos: Puntos?=null,
    val reciclajes: MutableList<Reciclaje> = mutableListOf(),
) {
    fun fechaRegistroString(): LocalDate {
        fechaRegistro=java.time.LocalDate.now()
        return fechaRegistro
    }
    fun obtenerReciclajes(): List<Reciclaje> {
        return reciclajes.filter { it.usuario == this }
    }

    fun calcularPesoTotalPorMaterial(reciclajes: List<Reciclaje>): Pair<Double, Map<TipoMaterial, Double>> {
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
