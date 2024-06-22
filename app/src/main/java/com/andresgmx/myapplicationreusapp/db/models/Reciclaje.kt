package com.andresgmx.myapplicationreusapp.db.models

import com.andresgmx.myapplicationreusapp.db.models.enums.TipoMaterial
import java.util.Date

class Reciclaje(
    var material: TipoMaterial,
    var peso: Double,
    var fecha: Date,
    var usuario: Usuario,
) {
}