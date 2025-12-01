package com.example.levelupgamer.viewmodel

import com.example.levelupgamer.data.remote.dto.RespuestaLoginDto
import com.example.levelupgamer.data.remote.dto.RespuestaRegistroDto
import com.example.levelupgamer.data.repository.AuthRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AuthViewModelTest {

    private val dispatcherDePrueba = StandardTestDispatcher()
    private lateinit var repository: AuthRepository
    private lateinit var viewModel: AuthViewModel

    @Before
    fun antesDeCadaTest() {
        Dispatchers.setMain(dispatcherDePrueba)
        repository = mockk()
        viewModel = AuthViewModel(repository)
    }

    @After
    fun despuesDeCadaTest() {
        Dispatchers.resetMain()
    }

    @Test
    fun `login exitoso marca isLoggedIn true y limpia error`() = runTest(dispatcherDePrueba) {
        // Arrange
        coEvery { repository.login(any(), any()) } returns
                Result.success(RespuestaLoginDto(token = "abc123"))

        // Act
        viewModel.login("correo@ejemplo.com", "Abc123$%")
        dispatcherDePrueba.scheduler.advanceUntilIdle()

        // Assert
        val estado = viewModel.uiState.value
        assertTrue(estado.isLoggedIn)
        assertFalse(estado.estaCargando)
        assertNull(estado.error)
    }

    @Test
    fun `login con fallo de conexion muestra mensaje amigable`() = runTest(dispatcherDePrueba) {
        // Arrange: simulamos error de red
        coEvery { repository.login(any(), any()) } returns
                Result.failure(Exception("failed to connect to /100.30.155.116 (port 8080)"))

        // Act
        viewModel.login("correo@ejemplo.com", "Abc123$%")
        dispatcherDePrueba.scheduler.advanceUntilIdle()

        // Assert
        val estado = viewModel.uiState.value
        assertFalse(estado.isLoggedIn)
        assertFalse(estado.estaCargando)
        assertEquals(
            "No se pudo contactar al servidor. Verifique su conexión o inténtelo más tarde.",
            estado.error
        )
    }

    @Test
    fun `registro exitoso marca registroExitoso true y limpia error`() = runTest(dispatcherDePrueba) {
        // Arrange
        coEvery { repository.register(any(), any()) } returns
                Result.success(RespuestaRegistroDto(id = "1", correo = "correo@ejemplo.com"))

        // Act
        viewModel.registro("correo@ejemplo.com", "Abc123$%")
        dispatcherDePrueba.scheduler.advanceUntilIdle()

        // Assert
        val estado = viewModel.uiState.value
        assertTrue(estado.registroExitoso)
        assertFalse(estado.estaCargando)
        assertNull(estado.error)
    }
}