package com.andresgmx.myapplicationreusapp.db.models;
import com.andresgmx.myapplicationreusapp.db.models.enums.TipoVia;

public class Direccion(
    var id: Long? = null,
    var numeroVia: Int? = null,
    var tipoVia: TipoVia? = null,
    var complemento: String? = null,
    var barrio: String? = null,
    var comuna: String? = null,
    var codigoPostal: String? = null,
){

    override fun toString(): String {
        return "Direccion(id=$id, numeroVia=$numeroVia, tipoVia=$tipoVia, complemento=$complemento, barrio=$barrio, comuna=$comuna, codigoPostal=$codigoPostal)"
    }

}