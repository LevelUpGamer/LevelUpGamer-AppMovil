package com.example.levelupgamer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class OfertasAdapter : RecyclerView.Adapter<OfertasAdapter.OfertaViewHolder>() {

    //  3 imágenes del carrusel
    private val listaOfertas = listOf(
        R.drawable.ofertab,
        R.drawable.ofertadmc,
        R.drawable.ofertasvst
    )

    inner class OfertaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgOferta: ImageView = itemView.findViewById(R.id.imgOferta1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfertaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_oferta, parent, false)
        return OfertaViewHolder(view)
    }

    override fun onBindViewHolder(holder: OfertaViewHolder, position: Int) {
        // 🔹 Muestra la imagen según su posición
        holder.imgOferta.setImageResource(listaOfertas[position])
    }

    override fun getItemCount(): Int = listaOfertas.size
}
