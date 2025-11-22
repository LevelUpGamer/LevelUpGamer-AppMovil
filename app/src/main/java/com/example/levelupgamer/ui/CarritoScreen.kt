package com.example.levelupgamer.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.levelupgamer.CarritoManager
import com.example.levelupgamer.Producto
import com.example.levelupgamer.R
import java.text.NumberFormat
import java.util.Locale

@Composable
fun CarritoScreen() {
    //Estado de la lista visible en la UI (CarritoManager)
    var items by remember { mutableStateOf(CarritoManager.items) }

    // Recalcular total cada vez que cambien los ítems
    val total = remember(items) { CarritoManager.calcularTotal() }

    //Formateo del total
    val format = remember {
        NumberFormat.getNumberInstance(Locale("es","CL")).apply {
            maximumFractionDigits = 0
            minimumFractionDigits = 0
        }
    }
    val totalFormateado = format.format(total)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        //Título
        Text(
            text = "Tu carrito",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            //color = colorResource(id = R.color.white),
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp)
        )

        //Lista de productos
        if (items.isEmpty()) {
            Text(
                text = "Tu carrito está vacío",
                fontSize = 16.sp,
                //color = colorResource(id = R.color.white)
                color = MaterialTheme.colorScheme.onBackground
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = items
                    //key = {it.id}
                ) { producto ->
                    ItemCarritoRow(
                        producto = producto,
                        onEliminar = {
                            CarritoManager.eliminarProducto(producto)
                            items = CarritoManager.items
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Total
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Total: ",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                //color = colorResource(id = R.color.white)
                color = MaterialTheme.colorScheme.onBackground
                )
            Text(
                text = "$$totalFormateado",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                //color = colorResource(id = R.color.green_accent)
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Composable
private fun ItemCarritoRow(
    producto: Producto,
    onEliminar: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Imagen del juego
        Image(
            painter = painterResource(id = producto.imagenResId),
            contentDescription = producto.nombre,
            modifier = Modifier
                .width(80.dp)
                .height(100.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        //Nombre y precio
        Column(
            modifier = Modifier.weight(1f)
        ){
            Text(
                text = producto.nombre,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                //color = colorResource(id = R.color.white),
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Precio: $${producto.precio.toInt()}",
                fontSize = 14.sp,
                //color = colorResource(id = R.color.white),
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        //Botón de basurero
        IconButton(
            onClick = onEliminar
        ) {
            Icon(
                painter = painterResource(id = R.drawable.outline_delete_24),
                contentDescription = "Eliminar ítem del carrito",
               // tint = colorResource(id = R.color.white)
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}