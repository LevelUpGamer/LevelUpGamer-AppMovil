package com.example.levelupgamer.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.levelupgamer.Producto
import com.example.levelupgamer.R


private val listaOfertas = listOf(
    R.drawable.ofertab,
    R.drawable.ofertadmc,
    R.drawable.ofertasvst
)

@Composable
fun HomeScreen(
    productos: List<Producto>,
    buscarConsulta: String,
    hayItemsCarrito: Boolean,
    onIrAlCarrito: () -> Unit
) {
    val resultadosBusqueda = if (buscarConsulta.isNotBlank()) {
        productos.filter { it.nombre.contains(buscarConsulta, ignoreCase = true) }
    } else {
        emptyList()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Bienvenida / Título ppal
        Text(
            text = "Bienvenid@ a Level Up Gamer",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp)
        )

        // Subtítulo / Sección ofertas semanales
        Text(
            text = "Ofertas de la semana",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .padding(bottom = 8.dp)
        )

        // Carrusel horizontal
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        ) {
            items(listaOfertas) { resId ->
                OfertaItemImagen(resId)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Resultados de búsqueda, en caso de que la haya
        if (buscarConsulta.isNotBlank()) {
            Text(
                text = "Resultados para \"$buscarConsulta\"",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground,
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (resultadosBusqueda.isEmpty()) {
                Text(
                    text = "No se encontraron juegos.",
                    color = MaterialTheme.colorScheme.onBackground
                )
            } else {
                resultadosBusqueda.forEach { producto ->
                    Text(
                        text = "• ${producto.nombre}",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // El botón de ir al carrito se muestra sólo si hay productos
        if (hayItemsCarrito) {
            Button(
                onClick = onIrAlCarrito,
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Ir al carrito")
            }
        }
    }
}

@Composable
fun OfertaItemImagen(resId: Int) {
    Card(
        modifier = Modifier
            .width(260.dp)
            .fillMaxHeight(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Image(
            painter = painterResource(id = resId),
            contentDescription = "OFerta destacada",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }

}
