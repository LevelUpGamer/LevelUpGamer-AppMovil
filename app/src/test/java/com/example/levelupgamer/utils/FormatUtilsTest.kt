package com.example.levelupgamer.utils

import org.junit.Test
import org.junit.Assert.assertEquals

class FormatUtilsTest {

    @Test
    fun `formatearPesos formatea numeros correctamente`() {
        val valor = 29990.0
        val resultado = valor.formatearPesos()
        assertEquals("$29.990", resultado)
    }

    @Test
    fun `formatearPesos funciona con valores grandes`() {
        val valor = 1523490.0
        val resultado = valor.formatearPesos()
        assertEquals("$1.523.490", resultado)
    }

    @Test
    fun `formatearPesos formatea cero correctamente`() {
        val valor = 0.0
        val resultado = valor.formatearPesos()
        assertEquals("$0", resultado)
    }

    @Test
    fun `formatearPesos redondea decimales`() {
        val valor = 19990.7
        val resultado = valor.formatearPesos()
        // "%,.0f" redondea hacia arriba en .7 → 19.991
        assertEquals("$19.991", resultado)
    }
}