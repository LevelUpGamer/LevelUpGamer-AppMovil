package com.example.levelupgamer.viewmodel

import com.example.levelupgamer.Producto
import com.example.levelupgamer.model.navigation.NavigationEvent
import com.example.levelupgamer.model.navigation.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    private lateinit var viewModel: MainViewModel
    private val dispatcherDePrueba = StandardTestDispatcher()

    @Before
    fun antesDeCadaTest() {
        Dispatchers.setMain(dispatcherDePrueba)
        viewModel = MainViewModel()
    }

    @After
    fun despuesDeCadaTest() {
        Dispatchers.resetMain()
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
    fun `onDrawerItemClick actualiza selected y cierra drawer`() = runTest(dispatcherDePrueba) {
        // Prepara una corrutina que escuche el flujo y guarde el evento
        var eventoRecibido: NavigationEvent? = null
        val job = launch {
            eventoRecibido = viewModel.eventos.first()  // se completa cuando llegue el primer emit
        }

        // Ejecuta la acción que dispara viewModelScope.launch { _eventos.emit(...) }
        viewModel.onDrawerItemClick(Screen.Catalogo)

        // Deja avanzar todas las corrutinas pendientes
        dispatcherDePrueba.scheduler.advanceUntilIdle()

        // Verifica el estado del Drawer
        val estado = viewModel.estadoUi.value
        assertEquals(Screen.Catalogo, estado.drawer.selected)
        assertFalse(estado.drawer.open)

        // Verifica que realmente se haya recibido el evento
        assertNotNull(eventoRecibido)
        assertTrue(eventoRecibido is NavigationEvent.NavigateTo)
        assertEquals(Screen.Catalogo, (eventoRecibido as NavigationEvent.NavigateTo).route)

        // Cancela la corrutina que estaba colectando, por si acaso
        job.cancel()
    }

    // Navegación
    @Test
    fun `retorno emite PopBackStack`() = runTest(dispatcherDePrueba) {
        var eventoRecibido: NavigationEvent? = null
        val job = launch {
            eventoRecibido = viewModel.eventos.first()
        }

        viewModel.retorno()

        dispatcherDePrueba.scheduler.advanceUntilIdle()

        assertNotNull(eventoRecibido)
        assertTrue(eventoRecibido is NavigationEvent.PopBackStack)

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