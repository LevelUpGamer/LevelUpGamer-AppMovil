package com.example.levelupgamer.model.navigation

sealed class Screen(val route: String) {
    data object Home: Screen (route = "home")
    data object Catalogo: Screen (route = "catalogo")
    data object Carrito: Screen(route = "carrito")
    data object CerrarSesion: Screen(route = "cerrar_sesion")
}