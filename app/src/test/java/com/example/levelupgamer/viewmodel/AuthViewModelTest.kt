package com.example.levelupgamer.viewmodel

import com.example.levelupgamer.Usuario
import com.example.levelupgamer.data.repository.AuthRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
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
        // Arrange: simulamos que el repositorio responde OK
        coEvery { repository.login(any(), any()) } returns
                Result.success("Login OK")   // <- Result<String>

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
    fun `login con fallo de conexion expone mensaje de la excepcion`() = runTest(dispatcherDePrueba) {
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

        // ahora comprobamos que el mensaje es el mismo de la excepción
        assertNotNull(estado.error)
        assertTrue(estado.error!!.contains("failed to connect", ignoreCase = true))
    }

    @Test
    fun `registro exitoso marca registroExitoso true y limpia error`() = runTest(dispatcherDePrueba) {
        // Arrange: simulamos que el registro es correcto
        coEvery { repository.register(any(), any()) } returns
                Result.success(
                    Usuario(
                        email = "correo@ejemplo.com",
                        password = "Abc123$%"
                    )
                )   // <- Result<Usuario>

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