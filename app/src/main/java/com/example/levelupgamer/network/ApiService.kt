package com.example.levelupgamer.network

import com.example.levelupgamer.Usuario
import com.example.levelupgamer.data.remote.dto.ProductoApiDto
import com.example.levelupgamer.data.remote.dto.RespuestaLoginDto
import com.example.levelupgamer.data.remote.dto.RespuestaRegistroDto
import com.example.levelupgamer.data.remote.dto.SolicitudDeLoginDto
import com.example.levelupgamer.data.remote.dto.SolicitudDeRegistroDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("productos") // Esto depende del endpoint en Spring Boot
    suspend fun getProductos(): List<ProductoApiDto> //Modificado Producto por ProductoApiDto

    @GET("productos/{id}")
    suspend fun getProducto(
        @Path("id") id: String
    ): ProductoApiDto

    @POST("productos")
    suspend fun crearProducto(
        @Body producto: ProductoApiDto
    ): ProductoApiDto

    @PUT("productos/{id}")
    suspend fun actualizarProducto(
        @Path("id") id: String,
        @Body producto: ProductoApiDto
    ): ProductoApiDto

    @DELETE("productos/{id}")
    suspend fun eliminarProducto(
        @Path("id") id: String
    )

    // ---------- AUTH ----------
    // Importante, ajustar las rutas a nuestro backend real --> "auth/login", "auth/register"


    @POST("auth/login")
    suspend fun login(@Body usuario: Usuario): Response<String> // texto plano

    @POST("auth/registro")
    suspend fun registro(@Body usuario: Usuario): Response<Usuario> // JSON
}