package com.example.levelupgamer.data.repository

import com.example.levelupgamer.Producto
import com.example.levelupgamer.network.RetrofitClient
import com.example.levelupgamer.R

class ProductoRepository {
    private val api = RetrofitClient.api

    suspend fun obtenerProductos(): List<Producto> {
        val remoto = api.getProductos() // List<ProductoDto>

        return remoto.map { dto ->
            // Mapear backend → modelo UI
            Producto(
                id = dto.id.toIntOrNull() ?: 0,
                nombre = dto.titulo,
                precio = dto.precio.toDouble(),
                imagenResId = R.drawable.eldenringportada // ???????????????
            )
        }
    }
}