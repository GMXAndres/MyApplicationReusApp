package com.andresgmx.myapplicationreusapp.db.models

import com.andresgmx.myapplicationreusapp.db.models.enums.TipoMaterial
import java.time.LocalDate


class Reciclaje(
    var material: TipoMaterial,
    var peso: Double,
    var fecha: LocalDate  = LocalDate.now(),
    var usuario: Usuario? = null,
) {
    override fun toString(): String {
        return "Reciclaje(material='$material', peso=$peso, fecha=$fecha, usuario=$usuario)"
    }

    fun ingresarUsuarioPorCedula(cedula: String, usuarios: List<Usuario>): Usuario {
        val usuario = usuarios.find { it.cedula == cedula }
        return usuario ?: throw IllegalArgumentException("No hay usuario con esa cédula")
    }





}