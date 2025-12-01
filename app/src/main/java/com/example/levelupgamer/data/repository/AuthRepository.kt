package com.example.levelupgamer.data.repository

import com.example.levelupgamer.data.remote.dto.RespuestaLoginDto
import com.example.levelupgamer.data.remote.dto.RespuestaRegistroDto
import com.example.levelupgamer.data.remote.dto.SolicitudDeLoginDto
import com.example.levelupgamer.data.remote.dto.SolicitudDeRegistroDto
import com.example.levelupgamer.network.RetrofitClient

class AuthRepository{

    private val api = RetrofitClient.api

    suspend fun login(correo: String, contrasena: String): Result<RespuestaLoginDto> {
        return try {
            val body = SolicitudDeLoginDto(correo = correo, contrasena = contrasena)
            val response = api.login(body)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun register(correo: String, contrasena: String): Result<RespuestaRegistroDto> {
        return try {
            val body = SolicitudDeRegistroDto(correo = correo, contrasena = contrasena)
            val response = api.register(body)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}