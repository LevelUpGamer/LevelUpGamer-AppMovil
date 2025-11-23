package com.example.levelupgamer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelupgamer.Producto
import com.example.levelupgamer.model.navigation.NavigationEvent
import com.example.levelupgamer.model.navigation.Screen
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// Estado del Drawer
data class DrawerUiState(
    val open: Boolean = false,
    val selected: Screen = Screen.Home
)

//Estado global de la app
data class AppUiState(
    val drawer: DrawerUiState = DrawerUiState(),
    val buscarConsulta: String = "",
    val cartItems: List<Producto> = emptyList()
)

class MainViewModel : ViewModel() {

    // Productos por ahora
    // Luego cambiar cuando haya API
    // ********************************************************************
    val eldenRing = com.example.levelupgamer.R.drawable.eldenringportada
    val resident4 = com.example.levelupgamer.R.drawable.resident4
    val godOfWar = com.example.levelupgamer.R.drawable.godofwar

    val productos: List<Producto> = listOf(
        Producto(
            id = 1,
            nombre = "Elden Ring",
            precio = 29_990.0,
            imagenResId = eldenRing
        ),
        Producto(
            id = 2,
            nombre = "Resident Evil 4",
            precio = 15_990.0,
            imagenResId = resident4
        ),
        Producto(
            id = 3,
            nombre = "God of War",
            precio = 24_990.0,
            imagenResId = godOfWar
        )
    )

    // Estado de la UI
    private val _estadoUi = MutableStateFlow(AppUiState())
    val estadoUi: StateFlow<AppUiState> = _estadoUi.asStateFlow()

    // Eventos de navegación
    private val _eventos = MutableSharedFlow<NavigationEvent>()
    val eventos = _eventos.asSharedFlow()

    // datos de negocio
    //------------------ AQUÍ CONECTAR API--------------------
    // CUANDO HAYA API, crear repositorio ProductoRepository y llamar con endpoints, Y guardar resultado en StateFlow
    // EJEMPLO PARA CUANDO HAYA API
    // private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    //val productos = _productos.asStateFlow()
    //
    //fun cargarProductos() {
    //    viewModelScope.launch {
    //        val respuesta = api.obtenerProductos()
    //        _productos.value = respuesta
    //    }
    //}

    // Drawer
    fun estadoDrawer(open: Boolean){
        _estadoUi.value = _estadoUi.value.copy(
            drawer = _estadoUi.value.drawer.copy(open = open)
        )
    }

    fun onDrawerItemClick(dest: Screen){
        _estadoUi.value = _estadoUi.value.copy(
            drawer = _estadoUi.value.drawer.copy(
                selected = dest,
                open = false
            )
        )
        viewModelScope.launch{
            _eventos.emit(NavigationEvent.NavigateTo(dest))
        }
    }

    fun retorno(){
        viewModelScope.launch {
            _eventos.emit(NavigationEvent.PopBackStack)
        }
    }

    // Búsqueda
    fun onBuscarConsultaChange(text: String){
        _estadoUi.value = _estadoUi.value.copy(buscarConsulta = text)
    }

    // Helper para filtrar productos
    fun filtrarProductos(): List<Producto> {
        val consulta = _estadoUi.value.buscarConsulta.trim()
        if (consulta.isBlank()) return productos

        return productos.filter {
            it.nombre.contains(consulta, ignoreCase = true)
        }
    }

    // Carrito
    fun agregarAlCarrito(producto: Producto){
        _estadoUi.update { state -> 
            state.copy(cartItems = state.cartItems + producto)
        }
    }

    // Quita sólo una unidad
    fun quitarUnoDelCarrito(producto: Producto){
        val actual = _estadoUi.value.cartItems.toMutableList()
        val index = actual.indexOfLast { it.id == producto.id }
        if (index != -1) {
            actual.removeAt(index)
            _estadoUi.value = _estadoUi.value.copy(cartItems = actual)
        }
    }

    //Eliminar todas las unidades de ese producto (al basurero)
    fun eliminarDelCarrito(producto: Producto){
        _estadoUi.update { state -> 
            state.copy(cartItems = state.cartItems.filterNot { it.id == producto.id })
        }
    }

    // Limpiar el carrito
    fun limpiarCarrito(){
        _estadoUi.update { state ->
            state.copy(cartItems = emptyList())
        }
    }

    fun calcularTotal(): Double{
        return _estadoUi.value.cartItems.sumOf { it.precio }
    }

}