package com.example.levelupgamer.model.navigation

import org.junit.Test
import org.junit.Assert.assertEquals

class ScreenTest {

    @Test
    fun `Screen Home tiene ruta correcta`() {
        assertEquals("home", Screen.Home.route)
    }

    @Test
    fun `Screen Catalogo tiene ruta correcta`() {
        assertEquals("catalogo", Screen.Catalogo.route)
    }

    @Test
    fun `Screen Carrito tiene ruta correcta`() {
        assertEquals("carrito", Screen.Carrito.route)
    }

    @Test
    fun `Screen CerrarSesion tiene ruta correcta`() {
        assertEquals("cerrar_sesion", Screen.CerrarSesion.route)
    }
}