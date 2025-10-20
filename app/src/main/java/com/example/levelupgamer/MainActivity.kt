package com.example.levelupgamer

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen // Importar para la splash screen
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.levelupgamer.adapter.OfertasAdapter
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var interruptorMenu: ActionBarDrawerToggle

    // Declaración correcta de la variable para controlar la visibilidad de la Splash Screen
    private var keepSplashScreen = true


    override fun onCreate(savedInstanceState: Bundle?) {

        // --- 1. Lógica de la Splash Screen (Debe ir PRIMERO) ---

        // Muestra la splash screen. Debe ir antes de super.onCreate().
        val splashScreen = installSplashScreen()

        // Opción para mantener la splash screen mientras se carga algo
        splashScreen.setKeepOnScreenCondition { keepSplashScreen }

        // --- 2. Inicialización de la Activity y su Contenido ---

        super.onCreate(savedInstanceState)
        // Usamos el layout_home que contiene tanto el RecyclerView como el DrawerLayout
        setContentView(R.layout.layout_home)

        // Simula la carga de datos
        // Cuando los datos estén listos, cambia 'keepSplashScreen' a false
        Handler(Looper.getMainLooper()).postDelayed({
            keepSplashScreen = false // La splash screen se descarta
            // Redirige a LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Cierra MainActivity para que el usuario no pueda volver
        }, 3000) // Mantiene la pantalla por 3 segundos para la demostración/carga

        // --- 3. Lógica del Layout Principal (RecyclerView y Navigation Drawer) ---

        // Referencia y configuración del RecyclerView
        val rvOfertas = findViewById<RecyclerView>(R.id.rv_ofertas_semanales)
        rvOfertas.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvOfertas.adapter = OfertasAdapter()

        // Configuración del Navigation Drawer
        drawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.navigation_view)

        // Botón de menú (ActionBarDrawerToggle)
        interruptorMenu = ActionBarDrawerToggle(
            this, drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawerLayout.addDrawerListener(interruptorMenu)
        // Habilita el ícono de la hamburguesa
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        interruptorMenu.syncState()

        // Manejar clics del menú (NavigationView)
        navView.setNavigationItemSelectedListener{item ->
            when (item.itemId) {
                R.id.nav_home -> Toast.makeText(this, "Inicio", Toast.LENGTH_SHORT).show()
                R.id.nav_catalogo -> Toast.makeText(this, "Catálogo", Toast.LENGTH_SHORT).show()
                R.id.nav_carrito -> Toast.makeText(this, "Carrito", Toast.LENGTH_SHORT).show()
                R.id.nav_cerrar_sesion -> Toast.makeText(this, "Cerrar sesión", Toast.LENGTH_SHORT).show()
            }
            drawerLayout.closeDrawers()
            true
        }
    }

    // Metodo para manejar el clic en el botón de la hamburguesa, el interruptor menú (ActionBarDrawerToggle)
    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        if (interruptorMenu.onOptionsItemSelected(item)) true
        else super.onOptionsItemSelected(item)
}