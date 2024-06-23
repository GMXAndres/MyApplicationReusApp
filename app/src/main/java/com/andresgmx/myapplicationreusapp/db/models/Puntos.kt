package com.andresgmx.myapplicationreusapp.db.models

import com.andresgmx.myapplicationreusapp.db.models.enums.TipoMaterial
import kotlin.random.Random

class Puntos(
    var cantidad: Int,
    private var codigo: String,
    val usuario: Usuario,
    val recompensas: PuntosRecompensas,
) {

    fun randomCode(): String {
        val caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        val longitud = 10
        val codigo = StringBuilder(longitud)

        for (i in 0 until longitud) {
            val indice = Random.nextInt(caracteres.length)
            codigo.append(caracteres[indice])
        }
        return codigo.toString()
    }
    init {
        codigo = randomCode()
    }

    init {
        cantidad=0
    }

    fun asignarPuntos(): Int{
        var pesoTotal = 0.0
        val materialesReciclados = mutableListOf<TipoMaterial>()
        for (reciclaje in usuario.reciclajes) {
            pesoTotal += reciclaje.peso
            materialesReciclados.add(reciclaje.material)
        }
        val puntosPorKilogramo: Int = 2
        val puntosPorMaterial = mapOf(
            TipoMaterial.AMARILLO to 4,
            TipoMaterial.AZUL to 5,
            TipoMaterial.ROJO to 6,
            TipoMaterial.VERDE to 3,
            TipoMaterial.NARANJA to 2,
            TipoMaterial.GRIS to 1
        )
        val puntosPorPeso = pesoTotal * puntosPorKilogramo
        val puntosPorMaterialReciclado = materialesReciclados.sumBy { puntosPorMaterial[it] ?: 0 }
        val total = puntosPorPeso * puntosPorMaterialReciclado
        return total.toInt()
    }
    init{
        cantidad += asignarPuntos()
    }

    init{
        cantidad -= recompensas.recompensa.restarPuntos()
    }

}
