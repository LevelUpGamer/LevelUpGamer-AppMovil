package com.example.levelupgamer.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.levelupgamer.model.navigation.Screen
import com.example.levelupgamer.viewmodel.DrawerUiState

@Composable
fun LevelUpDrawer(
    estadoDrawer: DrawerUiState,
    onSelect: (Screen) -> Unit,
    contenido: @Composable () -> Unit
) {
    val contenidoDrawer: @Composable () -> Unit = {
        ModalDrawerSheet {
            // aquí puedo poner el logo, no olvidar*****************
            listOf(Screen.Home, Screen.Catalogo, Screen.Perfil).forEach {

                //Esto es para que seleccione el ítem según pantalla :)
                val icono = when (it) {
                    Screen.Home -> Icons.Default.Home
                    Screen.Catalogo -> Icons.Default.List
                    Screen.Perfil -> Icons.Default.Person
                    else -> Icons.Default.Info
                }

                NavigationDrawerItem(
                    label = { Text(it.route) },
                    selected = estadoDrawer.selected == it,
                    onClick = { onSelect(it) },
                    icon = { Icon(icono, contentDescription = null) }
                )
            }
        }
    }

    ModalNavigationDrawer(
        drawerContent = contenidoDrawer,
        gesturesEnabled = estadoDrawer.open,
        content = contenido
    )
}