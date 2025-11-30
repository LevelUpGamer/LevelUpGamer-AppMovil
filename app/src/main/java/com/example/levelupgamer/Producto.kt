package com.example.levelupgamer

data class Producto(
    val id: Int,
    val nombre: String,
    val precio: Double,
    val imagenResId: Int // Para poder mostrar la imagen en el carrito
)