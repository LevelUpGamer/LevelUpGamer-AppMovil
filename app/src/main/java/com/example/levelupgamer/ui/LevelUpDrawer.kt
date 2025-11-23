package com.example.levelupgamer.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.levelupgamer.model.navigation.Screen
import com.example.levelupgamer.viewmodel.DrawerUiState
import com.example.levelupgamer.R

@Composable
fun LevelUpDrawer(
    estadoDrawer: DrawerUiState,
    onSelect: (Screen) -> Unit,
) {
        ModalDrawerSheet(
            drawerContainerColor = MaterialTheme.colorScheme.background,
            drawerContentColor = MaterialTheme.colorScheme.onBackground
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // LOGO
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.ic_levelupgamer),
                        contentDescription = "Level Up Gamer",
                        modifier = Modifier
                            .padding(20.dp)
                            .height(120.dp)
                    )
                }

                //ítems del menú
                listOf(Screen.Home, Screen.Catalogo, Screen.Carrito, Screen.CerrarSesion).forEach { screen ->

                    //Esto es para que seleccione el ítem según pantalla :)
                    val icono = when (screen) {
                        Screen.Home -> Icons.Filled.Home
                        Screen.Catalogo -> Icons.Filled.List
                        Screen.Carrito -> Icons.Filled.ShoppingCart
                        Screen.CerrarSesion -> Icons.Filled.ExitToApp
                        else -> Icons.Filled.Info
                    }

                    NavigationDrawerItem(
                        label = {
                            Text(
                                screen.route
                                    .replace("_"," ")
                                    .replaceFirstChar { c -> c.uppercase() }
                            )
                        },
                        selected = estadoDrawer.selected == screen,
                        onClick = { onSelect(screen) },
                        icon = { Icon(imageVector = icono, contentDescription = null) },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                // Footer  ©2025
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("©2025", fontWeight = FontWeight.Medium)
                }
            }
        }
}