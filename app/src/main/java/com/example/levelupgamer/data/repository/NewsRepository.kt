package com.example.levelupgamer.data.repository

import com.example.levelupgamer.data.remote.mmobomb.MmoBombRetrofitClient
import com.example.levelupgamer.data.remote.mmobomb.MmoNewsItem

class NewsRepository {
    private val api = MmoBombRetrofitClient.api

    suspend fun obtenerNoticiasRecientes(): List<MmoNewsItem> {
        return api.obtenerNoticiasRecientes()
    }
}