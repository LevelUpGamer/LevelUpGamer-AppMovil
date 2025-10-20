package com.example.levelupgamer

import android.content.Intent
import android.os.Bundle
import android.text.Layout
import android.view.Menu
import android.view.MenuItem
import com.example.levelupgamer.R
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.levelupgamer.view.CartActivity
import com.example.levelupgamer.view.LoginActivity
import com.example.levelupgamer.view.MainActivity
import com.google.android.material.navigation.NavigationView

class CatalogoActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_catalogo)

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

                R.id.nav_cerrar_sesion -> {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
            }
            drawerLayout.closeDrawers()
            true
        }
    }

}







