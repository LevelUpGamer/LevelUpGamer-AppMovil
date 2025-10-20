package com.example.levelupgamer

import android.content.Intent
import android.os.Bundle
// Importa Producto y CarritoManager para la lógica del carrito
import com.example.levelupgamer.Producto
import com.example.levelupgamer.CarritoManager
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.levelupgamer.view.CartActivity
import com.example.levelupgamer.view.LoginActivity
import com.example.levelupgamer.view.MainActivity
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar

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


        // 1. Definir los objetos Producto que serán agregados.
        //    (Asegúrate de que R.drawable.eldenringportada y resident4 existan)
        val eldenRing = Producto(
            id = 1,
            nombre = "Elden Ring",
            precio = 29990.0, // Usar Double
            imagenResId = R.drawable.eldenringportada
        )

        val residentEvil4 = Producto(
            id = 2,
            nombre = "Resident Evil 4",
            precio = 15990.0, // Usar Double
            imagenResId = R.drawable.resident4
        )

        // 2. Listener para el botón de ELDEN RING (ID: btnAgregar)
        val btnAgregarElden: Button = findViewById(R.id.btnAgregar)
        btnAgregarElden.setOnClickListener {
            CarritoManager.agregarProducto(eldenRing)
            Snackbar.make(it, "${eldenRing.nombre} agregado!", Snackbar.LENGTH_SHORT).show()
        }

        // 3. Listener para el botón de RESIDENT EVIL 4 (ID: btnAgregar2)
        val btnAgregarResident: Button = findViewById(R.id.btnAgregar2) // ¡Añadido!
        btnAgregarResident.setOnClickListener {
            CarritoManager.agregarProducto(residentEvil4)
            Snackbar.make(it, "${residentEvil4.nombre} agregado!", Snackbar.LENGTH_SHORT).show()
        }


        // Click en items del menú lateral
        navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> startActivity(Intent(this, MainActivity::class.java))
                R.id.nav_catalogo -> startActivity(Intent(this, CatalogoActivity::class.java))
                R.id.nav_carrito -> startActivity(Intent(this, CartActivity::class.java))
                R.id.nav_cerrar_sesion -> startActivity(Intent(this, LoginActivity::class.java))
            }
            drawerLayout.closeDrawers()
            true
        }
    }
}