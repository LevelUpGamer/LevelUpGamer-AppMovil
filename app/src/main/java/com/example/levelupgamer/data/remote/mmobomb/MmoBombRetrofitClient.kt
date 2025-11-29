package com.example.levelupgamer.data.remote.mmobomb

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MmoBombRetrofitClient {
    private const val BASE_URL = "https://www.mmobomb.com/"

    val api: MmoNewsApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MmoNewsApiService::class.java)
    }
}