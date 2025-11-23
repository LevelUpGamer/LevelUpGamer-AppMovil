package com.example.levelupgamer.utils

fun Double.formatearPesos(): String{
    return "$" + "%,.0f".format(this).replace(",", ".")
}