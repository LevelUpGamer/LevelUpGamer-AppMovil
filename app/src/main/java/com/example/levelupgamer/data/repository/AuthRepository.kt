package com.example.levelupgamer.data.repository

import com.example.levelupgamer.Usuario
import com.example.levelupgamer.network.ApiService
import com.example.levelupgamer.network.RetrofitClient
import retrofit2.HttpException

class AuthRepository(private val apiService: ApiService = RetrofitClient.api) {

    suspend fun login(email: String, password: String): Result<String> {
        return try {
            val response = apiService.login(Usuario(email, password))
            if (response.isSuccessful) {
                Result.success(response.body() ?: "Login exitoso")
            } else {
                Result.failure(Exception("Error ${response.code()}: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun register(email: String, password: String): Result<Usuario> {
        return try {
            val response = apiService.registro(Usuario(email, password))
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error ${response.code()}: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
