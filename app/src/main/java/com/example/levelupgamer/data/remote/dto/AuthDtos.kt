package com.example.levelupgamer.data.remote.dto

import com.google.gson.annotations.SerializedName

data class SolicitudDeLoginDto(
    @SerializedName("email")
    val correo: String,

    @SerializedName("password")
    val contrasena: String
)

data class RespuestaLoginDto(
    val token: String? = null,
    val mensaje: String? = null,
    val idUsuario: String? = null
)

data class SolicitudDeRegistroDto(
    @SerializedName("email")
    val correo: String,

    @SerializedName("password")
    val contrasena: String
)

data class RespuestaRegistroDto(
    val id: String? = null,
    val correo: String? = null,
    val mensaje: String? = null
)
