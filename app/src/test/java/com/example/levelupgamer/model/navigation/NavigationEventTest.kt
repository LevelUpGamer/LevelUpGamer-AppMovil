package com.example.levelupgamer.model.navigation

import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue

class NavigationEventTest {

    @Test
    fun `NavigateTo guarda correctamente la ruta`() {
        val event = NavigationEvent.NavigateTo(Screen.Catalogo)
        assertEquals(Screen.Catalogo, event.route)
    }

    @Test
    fun `NavigateTo permite popUpToRoute`() {
        val event = NavigationEvent.NavigateTo(
            route = Screen.Carrito,
            popUpToRoute = Screen.Home,
            inclusive = true,
            singleTop = true
        )

        assertEquals(Screen.Carrito, event.route)
        assertEquals(Screen.Home, event.popUpToRoute)
        assertTrue(event.inclusive)
        assertTrue(event.singleTop)
    }

    @Test
    fun `PopBackStack es un objeto singleton`() {
        val event = NavigationEvent.PopBackStack
        assertTrue(event is NavigationEvent.PopBackStack)
    }

    @Test
    fun `NavigateUp es un objeto singleton`() {
        val event = NavigationEvent.NavigateUp
        assertTrue(event is NavigationEvent.NavigateUp)
    }
}