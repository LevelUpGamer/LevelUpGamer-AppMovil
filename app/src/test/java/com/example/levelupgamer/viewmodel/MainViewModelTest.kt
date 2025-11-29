package com.example.levelupgamer.viewmodel

import com.example.levelupgamer.Producto
import com.example.levelupgamer.model.navigation.NavigationEvent
import com.example.levelupgamer.model.navigation.Screen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    private lateinit var viewModel: MainViewModel
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        viewModel = MainViewModel()
    }

    // Drawer
    @Test
    fun `estadoDrawer cambia open correctamente`() {
        viewModel.estadoDrawer(true)
        assertTrue(viewModel.estadoUi.value.drawer.open)

        viewModel.estadoDrawer(false)
        assertFalse(viewModel.estadoUi.value.drawer.open)
    }

    @Test
    fun `onDrawerItemClick actualiza selected y cierra drawer`() = runTest (dispatcher) {
        val job = launch { viewModel.eventos.first() }

        viewModel.onDrawerItemClick(Screen.Catalogo)

        val state = viewModel.estadoUi.value
        assertEquals(Screen.Catalogo, state.drawer.selected)
        assertFalse(state.drawer.open)

        job.cancel()
    }

    // Navegación
    @Test
    fun `retorno emite PopBackStack`() = runTest(dispatcher) {
        val job = launch {
            val evento = viewModel.eventos.first()
            assertTrue(evento is NavigationEvent.PopBackStack)
        }

        viewModel.retorno()

        job.cancel()
    }

    // Búsqueda
    @Test
    fun `onBuscarConsultaChange actualiza el texto`() {
        viewModel.onBuscarConsultaChange("elden")
        assertEquals("elden", viewModel.estadoUi.value.buscarConsulta)
    }

    @Test
    fun `filtrarProductos devuelve todos cuando la consulta esta vacia`() {
        val lista = viewModel.filtrarProductos()
        assertEquals(3, lista.size) // hay 3 productos en la lista del ViewModel
    }

    @Test
    fun `filtrarProductos filtra por nombre correctamente`() {
        viewModel.onBuscarConsultaChange("Elden")
        val resultado = viewModel.filtrarProductos()
        assertEquals(1, resultado.size)
        assertEquals("Elden Ring", resultado.first().nombre)
    }

    // Carrito
    @Test
    fun `agregarAlCarrito agrega un producto correctamente`() {
        val p = viewModel.productos.first()
        viewModel.agregarAlCarrito(p)

        val state = viewModel.estadoUi.value
        assertEquals(1, state.cartItems.size)
        assertEquals(p, state.cartItems.first())
    }

    @Test
    fun `quitarUnoDelCarrito elimina solo una unidad`() {
        val p = viewModel.productos.first()

        viewModel.agregarAlCarrito(p)
        viewModel.agregarAlCarrito(p)
        viewModel.quitarUnoDelCarrito(p)

        val state = viewModel.estadoUi.value
        assertEquals(1, state.cartItems.size)
    }

    @Test
    fun `eliminarDelCarrito borra todas las unidades de un producto`() {
        val p = viewModel.productos.first()

        viewModel.agregarAlCarrito(p)
        viewModel.agregarAlCarrito(p)
        viewModel.eliminarDelCarrito(p)

        val state = viewModel.estadoUi.value
        assertTrue(state.cartItems.isEmpty())
    }

    @Test
    fun `limpiarCarrito deja la lista vacia`() {
        val p = viewModel.productos.first()

        viewModel.agregarAlCarrito(p)
        viewModel.agregarAlCarrito(p)
        viewModel.limpiarCarrito()

        val state = viewModel.estadoUi.value
        assertTrue(state.cartItems.isEmpty())
    }

    @Test
    fun `calcularTotal suma correctamente`() {
        val lista = listOf(
            Producto(1, "A", 10.0, 0),
            Producto(2, "B", 5.0, 0)
        )

        // Simula que están en el carrito
        viewModel.limpiarCarrito()
        lista.forEach { viewModel.agregarAlCarrito(it) }

        val total = viewModel.calcularTotal()
        assertEquals(15.0, total, 0.0001)
    }
}