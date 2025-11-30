package com.example.levelupgamer.network

import com.example.levelupgamer.Producto
import retrofit2.http.GET

interface ApiService {
    @GET("productos") // Esto depende de tu endpoint en Spring Boot
    suspend fun getProductos(): List<Producto>
}