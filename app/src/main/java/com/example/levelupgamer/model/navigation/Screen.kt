package com.example.levelupgamer.model.navigation

sealed class Screen(val route: String) {
    data object Home: Screen (route = "home_page")
    data object Profile: Screen (route = "profile_page")
    data object Catalog: Screen (route = "catalog_page")

    data class Detail (val itemId: String) : Screen(route = "detail_page/{itemId}"){
        fun buidRoute():String {
            return route.replace("{itemId}", itemId)
        }
    }
}