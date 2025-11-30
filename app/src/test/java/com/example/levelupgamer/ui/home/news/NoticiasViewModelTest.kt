package com.example.levelupgamer.ui.home.news

import com.example.levelupgamer.data.remote.mmobomb.MmoNewsItem
import com.example.levelupgamer.data.repository.NewsRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

@OptIn(ExperimentalCoroutinesApi::class)
class NoticiasViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var repository: NewsRepository
    private lateinit var viewModel: NoticiasViewModel

    @Before
    fun setup() {
        // Redirigimos Dispatchers.Main al dispatcher de pruebas
        Dispatchers.setMain(testDispatcher)

        // Creamos un mock del repositorio
        repository = mockk()

        // Inyectamos el mock en el ViewModel
        viewModel = NoticiasViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `cargarNoticias exito actualiza lista y apaga cargando`() = runTest {
        // Arrange
        val fakeNoticias = listOf(
            MmoNewsItem(id = 1, titulo = "Noticia 1"),
            MmoNewsItem(id = 2, titulo = "Noticia 2")
        )

        coEvery { repository.obtenerNoticiasRecientes() } returns fakeNoticias

        // Act
        viewModel.cargarNoticias()
        testDispatcher.scheduler.advanceUntilIdle() // dejamos que termine la corrutina

        // Assert
        val estado = viewModel.uiState
        assertFalse(estado.cargando)
        assertEquals(fakeNoticias, estado.noticias)
        assertNull(estado.error)
    }

    @Test
    fun `cargarNoticias error deja lista vacia y setea error`() = runTest {
        // Arrange
        val exception = RuntimeException("Falla de red")
        coEvery { repository.obtenerNoticiasRecientes() } throws exception

        // Act
        viewModel.cargarNoticias()
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val estado = viewModel.uiState
        assertFalse(estado.cargando)
        assertTrue(estado.noticias.isEmpty())
        assertEquals("Falla de red", estado.error)
    }
}