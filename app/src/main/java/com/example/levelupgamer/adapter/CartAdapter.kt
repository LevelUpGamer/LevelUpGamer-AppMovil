

package com.example.levelupgamer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.levelupgamer.CarritoManager
import com.example.levelupgamer.Producto
import com.example.levelupgamer.R

// Asegúrate de que CarritoManager y Producto estén accesibles o importados

class CartAdapter(
    private var items: List<Producto>,
    private val onItemRemovedCallback: (() -> Unit)? = null) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    // 1. Define el ViewHolder: Mantiene las referencias a las vistas de un ítem.
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val nombreJuego: TextView = view.findViewById(R.id.text_nombre_juego)
        val precio: TextView = view.findViewById(R.id.text_precio)
        val imagen: ImageView = view.findViewById(R.id.image_juego)

        val basurero: ImageButton= view.findViewById(R.id.btn_eliminar_item)
    }

    //Crea nuevas vistas (Layout Manager llama a esto)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Reemplaza 'layout_item_carrito' con el nombre real de tu layout para un solo producto
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_carrito, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val producto = items[position]
        holder.nombreJuego.text = producto.nombre
        holder.precio.text = "$${String.format("%.2f", producto.precio)}"
        holder.imagen.setImageResource(producto.imagenResId)
        // Aquí iría el código para el ImageButton de la papelera
        holder.basurero.setOnClickListener {
            CarritoManager.eliminarProducto(producto)
            updateItems(CarritoManager.items)
            onItemRemovedCallback?.invoke()
        }
    }


    override fun getItemCount() = items.size


    fun updateItems(newItems: List<Producto>) {
        this.items = newItems
        notifyDataSetChanged() // Le dice al RecyclerView que redibuje toda la lista.
    }
}