package com.example.levelupgamer.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.levelupgamer.R
import com.example.levelupgamer.model.navigation.Screen
import com.example.levelupgamer.ui.LevelUpDrawer
import com.example.levelupgamer.viewmodel.DrawerUiState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
class DrawerActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var ui by remember { mutableStateOf(DrawerUiState()) }
            val estadoDrawer = rememberDrawerState(DrawerValue.Closed)
            val scope = rememberCoroutineScope()

            ModalNavigationDrawer(
                drawerState = estadoDrawer,
                gesturesEnabled = false,   // para que no haga swipe
                drawerContent = {
                    LevelUpDrawer(
                        estadoDrawer = ui.copy(open = estadoDrawer.isOpen),
                        onSelect = { screen ->
                            when (screen) {
                                Screen.CerrarSesion -> {
                                    startActivity(Intent(this, LoginActivity::class.java))
                                    finish()
                                }

                                else -> {
                                    ui = ui.copy(selected = screen)
                                    scope.launch { estadoDrawer.close() }
                                }
                            }
                        },
                        contenido = {} // sin uso
                    )
                }
            ) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(ui.selected.route.replaceFirstChar { it.uppercase() }) },
                            navigationIcon = {
                                IconButton(
                                    onClick = { scope.launch { estadoDrawer.open() } }
                                ) {
                                    Icon(Icons.Filled.Menu, contentDescription = "Menú")
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    Box(Modifier.Companion.padding(innerPadding)) {
                        when (ui.selected) {
                            Screen.Home -> AndroidView(
                                factory = { ctx ->
                                    LayoutInflater.from(ctx)
                                        .inflate(R.layout.layout_home, null, false)
                                }
                            )

                            Screen.Catalogo -> AndroidView(
                                factory = { ctx ->
                                    LayoutInflater.from(ctx)
                                        .inflate(R.layout.layout_catalogo, null, false)
                                }
                            )

                            Screen.Carrito -> AndroidView(
                                factory = { ctx ->
                                    LayoutInflater.from(ctx)
                                        .inflate(R.layout.layout_carrito, null, false)
                                }
                            )

                            else -> {}
                        }
                    }
                }
            }
        }
    }
}