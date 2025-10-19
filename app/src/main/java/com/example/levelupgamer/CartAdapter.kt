package com.example.levelupgamer

// Importa las vistas necesarias
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yourpackage.R // Asegúrate de cambiar esto
import com.yourpackage.data.CartItem // Usa tu data class

class CartAdapter(
    private val items: MutableList<CartItem>,
    // Función lambda para notificar cuando se hace clic en eliminar
    private val onDeleteClicked: (CartItem) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.image_item)
        val nameTextView: TextView = view.findViewById(R.id.text_item_name)
        val priceTextView: TextView = view.findViewById(R.id.text_item_price)
        val deleteButton: ImageButton = view.findViewById(R.id.image_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = items[position]
        holder.nameTextView.text = item.name
        holder.priceTextView.text = item.price
        holder.imageView.setImageResource(item.imageResId)

        holder.deleteButton.setOnClickListener {
            onDeleteClicked(item)
        }
    }

    override fun getItemCount() = items.size

    fun removeItem(item: CartItem) {
        val position = items.indexOf(item)
        if (position != -1) {
            items.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}