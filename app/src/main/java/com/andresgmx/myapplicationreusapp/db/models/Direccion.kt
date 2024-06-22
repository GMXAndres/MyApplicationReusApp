package com.andresgmx.myapplicationreusapp.db.models;
import com.andresgmx.myapplicationreusapp.db.models.enums.TipoVia;

public class Direccion(
    var numeroVia: Int,
    var tipoVia: TipoVia,
    var complemento: String,
    var barrio: String,
    var comuna: String,
    var codigoPostal: Int ,
){

}