package com.andresgmx.myapplicationreusapp.db

class ModelUser {
    var id: Int = 0
    var nombre: String? = null
    var apellido: String? = null
    var cedula: Long = 0
    var puntos: Int = 0
    var telefono: Int = 0
    var direccion: String? = null
    var fechaRegistro: String? = null
    var nombreUsuario: String? = null
    var contrasena: String? = null
    var correo: String? = null

    constructor()

    // Constructor simplificado para la visualización de puntos y nombres
    constructor(nombre: String, apellido: String, puntos: Int) {
        this.nombre = nombre
        this.apellido = apellido
        this.puntos = puntos
    }

    // Constructor completo para otros propósitos
    constructor(id: Int, nombre: String?, apellido: String?, cedula: Long, puntos: Int, telefono: Int, direccion: String?, fechaRegistro: String?, nombreUsuario: String?, contrasena: String?, correo: String?) {
        this.id = id
        this.nombre = nombre
        this.apellido = apellido
        this.cedula = cedula
        this.puntos = puntos
        this.telefono = telefono
        this.direccion = direccion
        this.fechaRegistro = fechaRegistro
        this.nombreUsuario = nombreUsuario
        this.contrasena = contrasena
        this.correo = correo
    }
}
