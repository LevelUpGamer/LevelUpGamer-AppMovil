package com.example.levelupgamer

data class Producto(
    val id: Int,
    val nombre: String,
    val precio: Double,
    val imagenResId: Int = 0, // Para poder mostrar la imagen en el carrito
    val imagenUrl: String? = null
)