package com.example.levelupgamer.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.levelupgamer.Producto
import com.example.levelupgamer.ui.theme.LevelUpPurpleAccent
import com.example.levelupgamer.ui.theme.LevelUpPurplePrimary
import com.example.levelupgamer.ui.theme.LevelUpWhite
import com.example.levelupgamer.utils.formatearPesos
import kotlinx.coroutines.launch
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import com.example.levelupgamer.util.vibrate

@Composable
fun CatalogoScreen(
    productos: List<Producto>,
    cartItems: List<Producto>,
    onAgregarAlCarrito: (Producto) -> Unit,
    onQuitarUno: (Producto) -> Unit
) {
    val context = LocalContext.current

    // Snackbar
    val snackbarHostState = remember { SnackbarHostState() }
    val corrutina = rememberCoroutineScope()


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

            if (productos.isEmpty()){
                Text(
                    text = "No se encontraron juegos.",
                    color = MaterialTheme.colorScheme.onBackground
                )
            } else {
                // Para cada producto
                val cartItems = cartItems

                productos.forEach { producto ->
                    val cantidad = cartItems.count{ it.id == producto.id }

                    ProductoCard(
                        producto = producto,
                        cantidadEnCarrito = cantidad,
                        onAgregar = {
                            onAgregarAlCarrito(producto)
                            vibrate(context, 50)
                            //Snackbar de confirmación
                            corrutina.launch {
                                snackbarHostState.showSnackbar(
                                    message = "¡${producto.nombre} agregado con éxito!"
                                )
                            }
                        },
                        onQuitarUno = { onQuitarUno(producto) }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun ProductoCard(
    producto: Producto,
    cantidadEnCarrito: Int,
    onAgregar: () -> Unit,
    onQuitarUno: () -> Unit
) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = CardDefaults.shape,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
//            Image(
//                painter = painterResource(id = producto.imagenResId),
//                contentDescription = producto.nombre,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(240.dp),
//                contentScale = ContentScale.Crop
//            )
            val painter = if (!producto.imagenUrl.isNullOrBlank()) {
                rememberAsyncImagePainter(producto.imagenUrl)
            } else {
                painterResource(id = producto.imagenResId)
            }

            Image(
                painter = painter,
                contentDescription = producto.nombre,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp),
                contentScale = ContentScale.Crop
            )

            Text(
                text = producto.nombre,
                fontSize = 20.sp, // 16.sp
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .padding(top = 8.dp)
            )

            Text(
                text = producto.precio.formatearPesos(),
                fontSize = 18.sp, // 14.sp
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .padding(bottom = 8.dp)
            )

            if (cantidadEnCarrito == 0) {
                Button(
                    onClick = onAgregar,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LevelUpPurplePrimary,
                        contentColor = LevelUpWhite
                    )
                ) {
                    Text("Agregar")
                }
            } else {
                Row(
                    modifier = Modifier
                        .padding(bottom = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    IconButton(onClick = {
                        onQuitarUno()
                        vibrate(context, 60)
                    }){
                        Icon(Icons.Default.Remove, contentDescription = "Quitar uno")
                    }

                    // Cantidad en el carrito
                    Box(
                        modifier = Modifier
                            .background(LevelUpPurpleAccent, RoundedCornerShape(50))
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = cantidadEnCarrito.toString(),
                            color = LevelUpWhite,
                            fontWeight = FontWeight.Bold
                        )
                    }

                        IconButton(onClick = {
                            onAgregar()
                            vibrate(context, 50)
                        }) {
                            Icon(Icons.Default.Add, contentDescription = "Agregar uno")
                        }

                }
            }
        }
    }
}