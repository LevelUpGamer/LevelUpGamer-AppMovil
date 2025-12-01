package com.example.levelupgamer.data.remote.dto

data class SolicitudDeLoginDto(
    val correo: String,
    val contrasena: String
)

data class RespuestaLoginDto(
    val token: String? = null,
    val mensaje: String? = null,
    val idUsuario: String? = null
)

data class SolicitudDeRegistroDto(
    val correo: String,
    val contrasena: String
)

data class RespuestaRegistroDto(
    val id: String? = null,
    val correo: String? = null,
    val mensaje: String? = null
)