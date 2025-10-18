package com.example.levelupgamer.model.navigation

sealed class Screen(val route: String) {
    data object Home: Screen (route = "home")
    data object Perfil: Screen (route = "perfil")
    data object Catalogo: Screen (route = "catalogo")

    data class Detail (val itemId: String) : Screen(route = "detalle/{itemId}"){
        fun buidRoute():String {
            return route.replace("{itemId}", itemId)
        }
    }
}