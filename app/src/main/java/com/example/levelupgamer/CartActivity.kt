package com.example.levelupgamer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.levelupgamer.R
import com.example.levelupgamer.data.CartItem


// El adaptador recibe la lista de ítems y un lambda (función) para manejar la eliminación
class CartAdapter(
    private val items: MutableList<CartItem>,
    private val onDeleteClick: (CartItem) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    // El ViewHolder enlaza las vistas del item_cart.xml con los datos
    class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemName: TextView = view.findViewById(R.id.text_item_name)
        val itemPrice: TextView = view.findViewById(R.id.text_item_price)
        val itemImage: ImageView = view.findViewById(R.id.image_item)
        val deleteButton: ImageButton = view.findViewById(R.id.image_delete)
    }

    // 1. Crea la vista (infla el layout)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    // 2. Llena la vista con datos
    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = items[position]

        holder.itemName.text = item.name
        holder.itemPrice.text = item.price
        holder.itemImage.setImageResource(item.imageResId)

        // Configura el clic en el botón de eliminación
        holder.deleteButton.setOnClickListener {
            // Llama a la función que se definió en CartActivity
            onDeleteClick(item)
        }
    }

    // 3. Retorna la cantidad de ítems
    override fun getItemCount(): Int = items.size

    // Función para manejar la eliminación desde la Activity
    fun removeItem(item: CartItem) {
        val index = items.indexOf(item)
        if (index != -1) {
            items.removeAt(index)
            notifyItemRemoved(index)
            // notifyItemRangeChanged(index, items.size) // Opcional, si hay más cambios
        }
    }
}