package com.example.levelupgamer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_home) // tu layout principal

        // Referencia al RecyclerView del layout_home
        val rvOfertas = findViewById<RecyclerView>(R.id.rv_ofertas_semanales)

        // Configurar layout horizontal
        rvOfertas.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // Conectar adaptador con las 3 imágenes
        rvOfertas.adapter = OfertasAdapter()
    }
}
