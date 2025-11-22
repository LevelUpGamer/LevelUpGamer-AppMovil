package com.example.levelupgamer.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.levelupgamer.CarritoManager
import com.example.levelupgamer.Producto
import com.example.levelupgamer.R
import kotlinx.coroutines.launch

@Composable
fun CatalogoScreen() {
    val contexto = LocalContext.current

    // Snackbar
    val snackbarHostState = remember { SnackbarHostState() }
    val corrutina = rememberCoroutineScope()

    // Mis productos
    val productos = listOf(
        Producto(
            id = 1,
            nombre = "Elden Ring",
            precio = 29_990.0,
            imagenResId = R.drawable.eldenringportada
        ),
        Producto(
            id = 2,
            nombre = "Resident Evil",
            precio = 15_990.0,
            imagenResId = R.drawable.resident4
        ),
        Producto(
            id = 3,
            nombre = "God of War",
            precio = 24_990.0,
            imagenResId = R.drawable.godofwar
        )
    )

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            //Título de la sección
            Text(
                text = "¡Todos nuestros Juegos!",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 8.dp)
            )

            // Para cada producto
            productos.forEach { producto ->
                ProductoCard(
                    producto = producto,
                    onAgregar = {
                        CarritoManager.agregarProducto(producto)

                        //Mensaje de confirmación con Snackbar
                        corrutina.launch {
                            snackbarHostState.showSnackbar(
                                message = "¡${producto.nombre} agregado con éxito!"
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun ProductoCard(
    producto: Producto,
    onAgregar: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = CardDefaults.shape,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = producto.imagenResId),
                contentDescription = producto.nombre,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp),
                contentScale = ContentScale.Crop
            )

            Text(
                text = producto.nombre,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .padding(top = 8.dp)
            )

            Text(
                text = "$${producto.precio.toInt()}",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .padding(bottom = 8.dp)
            )

            Button(
                onClick = onAgregar,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier
                    .padding(bottom = 12.dp)
            ) {
                Text(
                    text = "Agregar"
                )
            }
        }
    }
}