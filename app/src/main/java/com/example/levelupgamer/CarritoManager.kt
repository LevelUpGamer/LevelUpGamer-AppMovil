package com.example.levelupgamer

data class Producto(
    val id: Int,
    val nombre: String,
    val precio: Double,
    val imagenResId: Int // Para poder mostrar la imagen en el carrito
)

object CarritoManager {
    // Lista para almacenar los productos. Usamos 'mutableListOf' para poder añadir y quitar.
    private val _items = mutableListOf<Producto>()

    // Propiedad pública que devuelve una copia inmutable.
    val items: List<Producto>
        get() = _items.toList()

    fun agregarProducto(producto: Producto) {
        _items.add(producto)
    }

    fun eliminarProducto(producto: Producto) {
        _items.remove(producto)
    }

    fun vaciarCarrito() {
        _items.clear()
    }

    fun calcularTotal(): Double {
        return _items.sumOf { it.precio }
    }
}