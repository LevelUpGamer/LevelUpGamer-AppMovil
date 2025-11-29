package com.example.levelupgamer.data.remote.mmobomb

import retrofit2.http.GET

interface MmoNewsApiService {
    //endpoint para obtener las noticias actuales gamer
    @GET("api1/latestnews")
    suspend fun obtenerNoticiasRecientes(): List<MmoNewsItem>
}