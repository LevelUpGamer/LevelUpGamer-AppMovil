package com.example.levelupgamer.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import com.example.levelupgamer.R
import com.example.levelupgamer.ui.theme.LevelUpAccentLight
import com.example.levelupgamer.ui.theme.LevelUpBlack
import com.example.levelupgamer.ui.theme.LevelUpGreenAccent
import com.example.levelupgamer.ui.theme.LevelUpPurpleDark
import com.example.levelupgamer.ui.theme.LevelUpPurplePrimary
import com.example.levelupgamer.ui.theme.LevelUpWhite

@Composable
fun LevelUpTopBar(
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    onMenuClick: () -> Unit,
    onCartClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Botón menú
        IconButton(onClick = onMenuClick) {
            Icon(
                painter = painterResource(id = R.drawable.ic_menu),
                contentDescription = "Menu"
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        //Barra de búsqueda
        TextField(
            value = searchText,
            onValueChange = onSearchTextChange,
            singleLine = true,
            placeholder = { Text("Buscar juegos") },
            modifier = Modifier
                .weight(1f)
                .height(45.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,

                // Texto
                focusedTextColor = LevelUpWhite,
                unfocusedTextColor = LevelUpWhite,
                disabledTextColor = LevelUpWhite,

                // Placeholder
                focusedPlaceholderColor = Color(0xFFBBBBBB),
                unfocusedPlaceholderColor = Color(0xFFBBBBBB),
                disabledPlaceholderColor = Color(0xFF888888),

                // Cursor
                cursorColor = LevelUpAccentLight,
                errorCursorColor = LevelUpGreenAccent,

                // Indicadores invisibles
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.width(8.dp))

        //Botón carrito
        IconButton(onClick = onCartClick){
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "Carrito"
            )
        }
    }
}