package com.example.levelupgamer.data.remote.mmobomb

import com.google.gson.annotations.SerializedName

data class MmoNewsItem(
    val id: Int? = null,

    @SerializedName("title")
    val titulo: String? = null,

    @SerializedName("short_description")
    val descripcionCorta: String? = null,

    @SerializedName("thumbnail")
    val miniatura: String? = null,

    @SerializedName("main_image")
    val imagenPrincipal: String? = null,

    @SerializedName("article_content")
    val contenidoArticulo: String? = null,

    @SerializedName("article_url")
    val urlArticulo: String? = null
)