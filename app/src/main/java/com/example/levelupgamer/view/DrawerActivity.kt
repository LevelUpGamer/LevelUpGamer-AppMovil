package com.example.levelupgamer.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.levelupgamer.model.navigation.Screen
import com.example.levelupgamer.ui.CarritoScreen
import com.example.levelupgamer.ui.CatalogoScreen
import com.example.levelupgamer.ui.HomeScreen
import com.example.levelupgamer.ui.LevelUpDrawer
import com.example.levelupgamer.ui.theme.LevelUpGamerTheme
import com.example.levelupgamer.viewmodel.MainViewModel
import kotlinx.coroutines.launch


class DrawerActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LevelUpGamerTheme {
                LevelUpRoot(
                    viewModel = viewModel,
                    onLogout = {
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LevelUpRoot(
    viewModel: MainViewModel,
    onLogout: () -> Unit
) {
    val estadoUi by viewModel.drawerAbierto.collectAsState()

    val drawerState = rememberDrawerState(
        initialValue = if (estadoUi.open) DrawerValue.Open else DrawerValue.Closed
    )
    val corrutinaDrawer = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = false, // Sólo se abre con el botón hamburguesa
        drawerContent = {
            LevelUpDrawer(
                estadoDrawer = estadoUi,
                onSelect = { screen ->
                    when (screen) {
                        Screen.CerrarSesion -> onLogout()
                        else -> {
                            viewModel.onDrawerItemClick(screen)
                            corrutinaDrawer.launch { drawerState.close() }
                        }
                    }
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = estadoUi.selected.route
                                .replace("_", " ")
                                .replaceFirstChar { it.uppercase() }
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                corrutinaDrawer.launch { drawerState.open() }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "Abrir menú"
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                // Lo que se debería mostrar en cada pantalla
                when (estadoUi.selected) {
                    Screen.Home -> HomeScreen(
                        onIrAlCarrito = {
                            viewModel.onDrawerItemClick(Screen.Carrito)
                        }
                    )
                    Screen.Catalogo -> CatalogoScreen()
                    Screen.Carrito -> CarritoScreen()
                    else -> { }
                }
            }
        }
    }
}