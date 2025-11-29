package com.example.levelupgamer.ui.home.news

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelupgamer.data.remote.mmobomb.MmoNewsItem
import com.example.levelupgamer.data.repository.NewsRepository
import androidx.compose.runtime.*
import kotlinx.coroutines.launch

data class EstadoNoticias(
    val cargando: Boolean = false,
    val noticias: List<MmoNewsItem> = emptyList(),
    val error: String? = null
)

class NoticiasViewModel(
    private val repository: NewsRepository = NewsRepository()
): ViewModel() {
    var uiState by mutableStateOf(EstadoNoticias())
        private set

    fun cargarNoticias(){
        uiState = uiState.copy(cargando = true, error = null)

        viewModelScope.launch {
            try {
                val items = repository.obtenerNoticiasRecientes()
                uiState = uiState.copy(
                    cargando = false,
                    noticias = items,
                    error = null
                )
            } catch (e: Exception) {
                uiState = uiState.copy(
                    cargando = false,
                    error = e.message ?: "Error al cargar noticias"
                )
            }
        }
    }
}