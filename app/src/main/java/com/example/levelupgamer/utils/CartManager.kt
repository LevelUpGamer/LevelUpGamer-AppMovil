package com.example.levelupgamer.utils

import com.example.levelupgamer.data.CartItem

class CartManager {

    object CartManager {
        private val items = mutableListOf<CartItem>()

        fun getCartItems(): List<CartItem> = items.toList()

        fun addItem(item: CartItem) {
            items.add(item)
        }

        // Opcional: Para contar los ítems en el carrito (útil para notificaciones)
        fun getCount(): Int = items.size
    }
}