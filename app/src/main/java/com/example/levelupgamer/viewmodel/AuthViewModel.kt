package com.example.levelupgamer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelupgamer.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


data class AuthUiState(
    val estaCargando: Boolean = false,
    val isLoggedIn: Boolean = false,
    val registroExitoso: Boolean = false,
    val error: String? = null
)

class AuthViewModel (
    private val repository: AuthRepository = AuthRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun login(correo: String, contrasena: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState(estaCargando = true)

            val result = repository.login(correo, contrasena)

            result.onSuccess {
                // Aquí se podría validar token
                _uiState.value = AuthUiState(
                    estaCargando = false,
                    isLoggedIn = true,
                    registroExitoso = false,
                    error = null
                )
            }.onFailure { e ->
                val mensajeError = if (
                    e.message?.contains("failed to connect", ignoreCase = true) == true ||
                    e.message?.contains("timed out", ignoreCase = true) == true
                ) {
                    "No se pudo contactar al servidor. Verifique su conexión o inténtelo más tarde."
                } else {
                    "Error al iniciar sesión"
                }

                _uiState.value = AuthUiState(
                    estaCargando = false,
                    isLoggedIn = false,
                    registroExitoso = false,
                    error = mensajeError
                )
            }
        }
    }

    fun registro(correo: String, contrasena: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                estaCargando = true,
                registroExitoso = false,
                error = null
            )

            val result = repository.register(correo, contrasena)

            result.onSuccess {
                _uiState.value = _uiState.value.copy(
                    estaCargando = false,
                    registroExitoso = true,
                    error = null
                )
            }.onFailure { e ->
                val mensajeError = if (
                    e.message?.contains("failed to connect", ignoreCase = true) == true ||
                    e.message?.contains("timed out", ignoreCase = true) == true
                ) {
                    "No se pudo contactar al servidor. Verifique su conexión o inténtelo más tarde."
                } else {
                    "Error al registrarse"
                }

                _uiState.value = _uiState.value.copy(
                    estaCargando = false,
                    registroExitoso = false,
                    error = mensajeError
                )
            }
        }
    }

    fun consumirRegistroExitoso() {
        _uiState.value = _uiState.value.copy(registroExitoso = false)
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}