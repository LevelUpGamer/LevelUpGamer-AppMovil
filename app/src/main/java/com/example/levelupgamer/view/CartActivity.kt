package com.example.levelupgamer.view

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager // IMPORTANTE: Necesitas esta importación
import androidx.recyclerview.widget.RecyclerView
import com.example.levelupgamer.CatalogoActivity
import com.example.levelupgamer.CarritoManager
import com.example.levelupgamer.R
import com.example.levelupgamer.adapter.CartAdapter
import com.example.levelupgamer.view.MainActivity
import com.example.levelupgamer.view.LoginActivity
import com.google.android.material.navigation.NavigationView

class CartActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var totalTextView: TextView
    // Puedes necesitar un lateinit var para el CarritoAdapter si quieres usarlo en varios sitios
    // private lateinit var carritoAdapter: CarritoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_carrito)

        drawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.navigation_view)

        // Inicialización de Vistas
        recyclerView = findViewById(R.id.recycler_view_cart)
        totalTextView = findViewById(R.id.text_total_value)

        // Configura el RecyclerView y el Adapter
        setupRecyclerView()

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
                R.id.nav_home -> startActivity(Intent(this, MainActivity::class.java))
                R.id.nav_catalogo -> startActivity(Intent(this, CatalogoActivity::class.java))
                R.id.nav_carrito -> startActivity(Intent(this, CartActivity::class.java))
                R.id.nav_cerrar_sesion -> startActivity(Intent(this, LoginActivity::class.java))
            }
            drawerLayout.closeDrawers()
            true
        }
    }



    // onResume se llama cada vez que la Activity vuelve al primer plano
    override fun onResume() {
        super.onResume()
        // Es clave actualizar la UI aquí para mostrar los productos agregados desde el Catálogo
        updateCartUI()
    }


    private fun setupRecyclerView() {
        // Asegúrate de que CarritoAdapter exista y acepte List<Producto>
        val adapter = CartAdapter(CarritoManager.items)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun updateCartUI() {
        // 1. Notificar al adapter que la lista de productos cambió
        (recyclerView.adapter as? CartAdapter)?.updateItems(CarritoManager.items)

        // 2. Actualizar el valor total
        val total = CarritoManager.calcularTotal()
        // Formato: Muestra el total con dos decimales, incluso si son cero
        totalTextView.text = "$${String.format("%.2f", total)}"
    }
}