package com.example.levelupgamer.ui.home.news

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.levelupgamer.data.remote.mmobomb.MmoNewsItem

@Composable
fun SeccionNoticias(
    viewModel: NoticiasViewModel = viewModel()
){
    val estado = viewModel.uiState
    val context = LocalContext.current

    // Cargar noticias al entrar
    LaunchedEffect(Unit) {
        viewModel.cargarNoticias()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = "Noticias gamer",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(8.dp))

        when {
            estado.cargando -> Text("Cargando noticias...")

            estado.error != null -> Text(
                text = "Error: ${estado.error}",
                color = MaterialTheme.colorScheme.error
            )

            estado.noticias.isEmpty() -> Text("No hay noticias disponibles.")

            else -> {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    estado.noticias.forEach { noticia ->
                        NewsCard(
                            item = noticia,
                            onClick = { url ->
                                if (!url.isNotBlank()) {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                    val pm = context.packageManager
                                    if (intent.resolveActivity(pm) != null) {
                                        context.startActivity(intent)
                                    }
                                }
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Fuente: MMOBomb.com",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}


@Composable
private fun NewsCard(
    item: MmoNewsItem,
    onClick: (String) -> Unit
) {
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                item.urlArticulo?.let(onClick)
            },
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row (
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.Top
        ) {
            item.miniatura?.let { url ->
                Image(
                    painter = rememberAsyncImagePainter(url),
                    contentDescription = item.titulo,
                    modifier = Modifier
                        .size(80.dp)
                        .padding(end = 8.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.titulo ?: "(Sin título)",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.descripcionCorta ?: "",
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 3
                )
            }
        }
    }
}