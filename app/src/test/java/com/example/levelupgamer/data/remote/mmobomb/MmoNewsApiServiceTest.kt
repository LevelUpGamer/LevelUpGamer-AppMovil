package com.example.levelupgamer.data.remote.mmobomb

import com.google.gson.GsonBuilder
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MmoNewsApiServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var api: MmoNewsApiService

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val gson = GsonBuilder().create()

        api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(MmoNewsApiService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `obtenerNoticiasRecientes parsea correctamente la lista de noticias`() = runBlocking {
        // JSON de ejemplo similar al de MmoBomb
        val body = """
            [
              {
                "id": 1,
                "title": "Titulo 1",
                "short_description": "Descripcion corta",
                "thumbnail": "https://example.com/thumb.jpg",
                "main_image": "https://example.com/main.jpg",
                "article_content": "Contenido del articulo",
                "article_url": "https://www.mmobomb.com/articulo"
              }
            ]
        """.trimIndent()

        mockWebServer.enqueue(
            MockResponse()
                .setBody(body)
                .setResponseCode(200)
        )

        val result = api.obtenerNoticiasRecientes()

        assertEquals(1, result.size)
        val item = result[0]
        assertEquals(1, item.id)
        assertEquals("Titulo 1", item.titulo)
        assertEquals("Descripcion corta", item.descripcionCorta)
    }
}