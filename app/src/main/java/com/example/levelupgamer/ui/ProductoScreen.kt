package com.example.levelupgamer.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.levelupgamer.Producto
import com.example.levelupgamer.viewmodel.MainViewModel
import com.example.levelupgamer.R

@Composable
fun ProductoScreen(
    viewModel: MainViewModel = viewModel()
) {
    val uiState by viewModel.estadoUi.collectAsState()
    val productos by viewModel.productos.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {

        // Barra de búsqueda
        OutlinedTextField(
            value = uiState.buscarConsulta,
            onValueChange = { viewModel.onBuscarConsultaChange(it) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Buscar juegos...") }
        )

        Spacer(modifier = Modifier.height(12.dp))

        //  Lista de productos filtrada
        val productosFiltrados = viewModel.filtrarProductos()

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(productosFiltrados) { item ->
                ProductoItem(
                    producto = item,
                    onAgregar = { viewModel.agregarAlCarrito(item) }
                )
            }
        }
    }
}

@Composable
fun ProductoItem(
    producto: Producto,
    onAgregar: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            //  Imagen: si viene imagenResId lo usamos, si no, un placeholder
            if (producto.imagenResId != 0) {
                Image(
                    painter = painterResource(id = producto.imagenResId),
                    contentDescription = producto.nombre,
                    modifier = Modifier
                        .size(80.dp)
                        .background(Color.LightGray),
                    contentScale = ContentScale.Crop
                )
            } else {
                AsyncImage(
                    model = "https://via.placeholder.com/150",
                    contentDescription = "placeholder",
                    modifier = Modifier.size(80.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(producto.nombre, fontWeight = FontWeight.Bold)
                Text("$${producto.precio}")
            }

            Button(onClick = onAgregar) {
                Text("Agregar")
            }
        }
    }
}
