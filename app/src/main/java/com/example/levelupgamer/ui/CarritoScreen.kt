package com.example.levelupgamer.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.levelupgamer.Producto
import com.example.levelupgamer.R
import com.example.levelupgamer.utils.formatearPesos
import java.text.NumberFormat
import java.util.Locale

@Composable
fun CarritoScreen(
    items: List<Producto>,
    total: Double,
    onQuitarUno: (Producto) -> Unit,
    onEliminar: (Producto) -> Unit,
    onPagar: () -> Unit
) {
    val context = LocalContext.current

    //Formateo del total
    val format = NumberFormat.getNumberInstance(Locale("es","CL")).apply {
            maximumFractionDigits = 0
            minimumFractionDigits = 0
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
                color = MaterialTheme.colorScheme.onBackground
            )
        } else {
            val productosAgrupados = items.groupBy { it.id }

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                items(productosAgrupados.entries.toList()) { (id, lista) ->
                    val producto = lista.first()
                    val cantidad = lista.size

                    ItemCarritoRow(
                        producto = producto,
                        cantidad = cantidad,
                        onQuitarUno = { onQuitarUno(producto)},
                        onEliminar = { onEliminar(producto) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total: ",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground
                )
//                Text(
//                    text = "$$totalFormateado",
//                    fontSize = 18.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = MaterialTheme.colorScheme.secondary
//                )
                Text(
                    text = total.formatearPesos(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    onPagar()
                    Toast.makeText(context, "Gracias por su compra", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Pagar")
            }
        }
    }
}

@Composable
private fun ItemCarritoRow(
    producto: Producto,
    cantidad: Int,
    onQuitarUno: () -> Unit,
    onEliminar: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        //    .padding(vertical = 4.dp),
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
//                fontSize = 16.sp,
//                fontWeight = FontWeight.Bold,
//                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                //text = "Precio: $${producto.precio.toInt()}  x $cantidad",
                text = "Precio: ${producto.precio.formatearPesos()} x $cantidad",
//                fontSize = 14.sp,
//                color = MaterialTheme.colorScheme.onBackground
            )
        }

        //Botón de basurero
//        IconButton(onClick = onEliminar) {
//            Icon(
//                painter = painterResource(id = R.drawable.outline_delete_24),
//                contentDescription = "Eliminar ítem del carrito",
//                tint = MaterialTheme.colorScheme.onBackground
//            )
//        }

        // Botón quitar uno solo
        IconButton(onClick = onQuitarUno) {
            Icon(Icons.Default.Remove, contentDescription = "Quitar uno")
        }

        // Basurero eliminar TODOS
        IconButton(onClick = onEliminar) {
            Icon(
                painter = painterResource(id = R.drawable.outline_delete_24),
                contentDescription = "Eliminar todos",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}