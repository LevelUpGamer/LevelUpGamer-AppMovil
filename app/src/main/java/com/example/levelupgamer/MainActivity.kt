package com.example.levelupgamer

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var interruptorMenu: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_home) // tu layout principal

        // Referencia al RecyclerView del layout_home
        val rvOfertas = findViewById<RecyclerView>(R.id.rv_ofertas_semanales)

        // Configurar layout horizontal
        rvOfertas.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // Conectar adaptador con las 3 imágenes
        rvOfertas.adapter = OfertasAdapter()

        drawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.navigation_view)

        //Botón menu
        interruptorMenu = ActionBarDrawerToggle(
            this, drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawerLayout.addDrawerListener(interruptorMenu)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        interruptorMenu.syncState()

        //Manejar clics del menú
        navView.setNavigationItemSelectedListener{item ->
            when (item.itemId) {
                R.id.nav_home -> Toast.makeText(this, "Inicio", Toast.LENGTH_SHORT).show()
                R.id.nav_catalogo -> Toast.makeText(this, "Catálogo", Toast.LENGTH_SHORT).show()
                R.id.nav_perfil -> Toast.makeText(this, "Perfil", Toast.LENGTH_SHORT).show()
            }
            drawerLayout.closeDrawers()
            true
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        if (interruptorMenu.onOptionsItemSelected(item)) true
        else super.onOptionsItemSelected(item)
}
