package com.example.levelupgamer.view

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.levelupgamer.CatalogoActivity
import com.example.levelupgamer.R
import com.example.levelupgamer.adapter.OfertasAdapter
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_home)

        // RecyclerView horizontal de ofertas
        val rvOfertas = findViewById<RecyclerView>(R.id.rv_ofertas_semanales)
        rvOfertas.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvOfertas.adapter = OfertasAdapter()

        drawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.navigation_view)

        // Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""

        // Botón hamburguesa manual
        val btnMenu: ImageButton = findViewById(R.id.btnMenu)
        btnMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        // Botón carrito
        val imageCart: ImageView = findViewById(R.id.btnCartHome)
        imageCart.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }

        // Click en items del menú lateral
        navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_catalogo -> {
                    val intent = Intent(this, CatalogoActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_carrito -> {
                    val intent = Intent(this, CartActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_cerrar_sesion ->{
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
            }
            drawerLayout.closeDrawers()
            true
        }
    }

}