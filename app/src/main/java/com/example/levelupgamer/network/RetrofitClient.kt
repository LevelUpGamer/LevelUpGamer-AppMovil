package com.example.levelupgamer.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://3.212.50.160:8080/" // asegúrate del puerto 8080

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create()) // <-- Para texto plano
            .addConverterFactory(GsonConverterFactory.create())    // <-- Para JSON
            .build()
            .create(ApiService::class.java)
    }
}
