package com.example.levelupgamer.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelupgamer.Producto
import com.example.levelupgamer.data.remote.dto.ProductoApiDto
import com.example.levelupgamer.data.repository.ProductoRepository
import com.example.levelupgamer.model.navigation.NavigationEvent
import com.example.levelupgamer.model.navigation.Screen
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// --- API Retrofit ---
interface ProductoApi {
    @GET("productos")
    suspend fun getProductos(): List<ProductoApiDto> // Modificado de Producto a ProductoApiDto
}

// --- Estado del Drawer ---
data class DrawerUiState(
    val open: Boolean = false,
    val selected: Screen = Screen.Home
)

// --- Estado global de la app ---
data class AppUiState(
    val drawer: DrawerUiState = DrawerUiState(),
    val buscarConsulta: String = "",
    val cartItems: List<Producto> = emptyList()
)

class MainViewModel : ViewModel() {

    private val productoRepository = ProductoRepository()

    // --- Retrofit para backend ---
    private val api: ProductoApi = Retrofit.Builder()
        .baseUrl("http://100.30.155.116:8080/") // <- EC2 con Spring Boot
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ProductoApi::class.java)

    // --- Productos cargados desde backend ---
    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos.asStateFlow()

    init {
        cargarProductos()
    }

    private fun cargarProductos() {
        viewModelScope.launch {
            try {
                val listaDto = api.getProductos()

                val listaUi = listaDto.map { dto ->
                    Producto(
                        id = dto.id.toIntOrNull() ?: 0,
                        nombre = dto.titulo,
                        precio = dto.precio.toDouble(),
                        imagenUrl = dto.imagen
                    )
                }

                _productos.value = listaUi

            } catch (e: Exception) {
                Log.e("API_ERROR", "Error al cargar productos: $e")
            }
        }
    }

    // --- Estado de la UI ---
    private val _estadoUi = MutableStateFlow(AppUiState())
    val estadoUi: StateFlow<AppUiState> = _estadoUi.asStateFlow()

    // --- Eventos de navegación ---
    private val _eventos = MutableSharedFlow<NavigationEvent>()
    val eventos = _eventos.asSharedFlow()

    // --- Drawer ---
    fun estadoDrawer(open: Boolean) {
        _estadoUi.value = _estadoUi.value.copy(
            drawer = _estadoUi.value.drawer.copy(open = open)
        )
    }

    fun onDrawerItemClick(dest: Screen) {
        _estadoUi.value = _estadoUi.value.copy(
            drawer = _estadoUi.value.drawer.copy(
                selected = dest,
                open = false
            )
        )
        viewModelScope.launch {
            _eventos.emit(NavigationEvent.NavigateTo(dest))
        }
    }

    fun retorno() {
        viewModelScope.launch {
            _eventos.emit(NavigationEvent.PopBackStack)
        }
    }

    // --- Búsqueda ---
    fun onBuscarConsultaChange(text: String) {
        _estadoUi.value = _estadoUi.value.copy(buscarConsulta = text)
    }

    fun filtrarProductos(): List<Producto> {
        val consulta = _estadoUi.value.buscarConsulta.trim()
        if (consulta.isBlank()) return _productos.value
        return _productos.value.filter { it.nombre.contains(consulta, ignoreCase = true) }
    }

    // --- Carrito ---
    fun agregarAlCarrito(producto: Producto) {
        _estadoUi.update { state ->
            state.copy(cartItems = state.cartItems + producto)
        }
    }

    fun quitarUnoDelCarrito(producto: Producto) {
        val actual = _estadoUi.value.cartItems.toMutableList()
        val index = actual.indexOfLast { it.id == producto.id }
        if (index != -1) {
            actual.removeAt(index)
            _estadoUi.value = _estadoUi.value.copy(cartItems = actual)
        }
    }

    fun eliminarDelCarrito(producto: Producto) {
        _estadoUi.update { state ->
            state.copy(cartItems = state.cartItems.filterNot { it.id == producto.id })
        }
    }

    fun limpiarCarrito() {
        _estadoUi.update { state ->
            state.copy(cartItems = emptyList())
        }
    }

    fun calcularTotal(): Double {
        return _estadoUi.value.cartItems.sumOf { it.precio }
    }
}
