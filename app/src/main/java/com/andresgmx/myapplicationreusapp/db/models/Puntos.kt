package com.andresgmx.myapplicationreusapp.db.models

import com.andresgmx.myapplicationreusapp.db.models.enums.TipoMaterial
import kotlin.random.Random

class Puntos(
    var id: Long? = null,
    var cantidad: Int=0,
    var codigo: String? = null,
    val usuario: Usuario,
    var recompensas: MutableList<PuntosRecompensas> = mutableListOf(),
) {
    override fun toString(): String {
        return "Puntos(cantidad=$cantidad, codigo=$codigo, usuario=$usuario, recompensas=$recompensas)"
    }
    fun agregarRecompensa(recompensa: Recompensas) {
        val puntosRecompensas = PuntosRecompensas(punto=this, recompensa=recompensa)
        recompensas.add(puntosRecompensas)
        recompensa.puntos.add(puntosRecompensas)
    }

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

    fun aplicarRecompensa(recompensa: Recompensas) {
        if (cantidad >= recompensa.minPuntos) {
            cantidad -= recompensa.minPuntos
        } else {
            throw IllegalArgumentException("No hay suficientes puntos para aplicar la recompensa")
        }
    }


}

