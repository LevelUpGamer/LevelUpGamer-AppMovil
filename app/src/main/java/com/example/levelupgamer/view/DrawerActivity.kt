package com.example.levelupgamer.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.levelupgamer.model.navigation.Screen
import com.example.levelupgamer.ui.CarritoScreen
import com.example.levelupgamer.ui.CatalogoScreen
import com.example.levelupgamer.ui.HomeScreen
import com.example.levelupgamer.ui.LevelUpDrawer
import com.example.levelupgamer.ui.LevelUpTopBar
import com.example.levelupgamer.ui.theme.LevelUpGamerTheme
import com.example.levelupgamer.viewmodel.AppUiState
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
    val estadoUi by viewModel.estadoUi.collectAsState()

    //Drawer y Navb controllers locales
    val estadoDrawer = rememberDrawerState(
        initialValue = if (estadoUi.drawer.open) DrawerValue.Open else DrawerValue.Closed
    )

    val corrutinaDrawer = rememberCoroutineScope()
    val navController: NavHostController = rememberNavController()

    // Cuando cambie la ruta seleccionada en el drawer, navegamos
    LaunchedEffect(estadoUi.drawer.selected) {
    //    val destino = estadoUi.drawer.selected
//        navController.navigate(destino.route){
//            launchSingleTop = true
        navController.navigate(estadoUi.drawer.selected.route){
            launchSingleTop = true
        }
    }

    ModalNavigationDrawer(
        drawerState = estadoDrawer,
        gesturesEnabled = false, // Sólo se abre con el botón hamburguesa
        drawerContent = {
            LevelUpDrawer(
                estadoDrawer = estadoUi.drawer,
                onSelect = { screen ->
//                    when (screen) {
//                        Screen.CerrarSesion -> onLogout()
//                        else -> {
//                            viewModel.onDrawerItemClick(screen)
//                            corrutinaDrawer.launch { estadoDrawer.close() }
//                        }
//                    }
                    if (screen == Screen.CerrarSesion){
                        onLogout()
                    } else {
                        viewModel.onDrawerItemClick(screen)
                        corrutinaDrawer.launch { estadoDrawer.close() }
                    }
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
//                TopAppBar(
//                    title = {
//                        Text(
//                            text = estadoUi.drawer.selected.route
//                                .replace("_", " ")
//                                .replaceFirstChar { it.uppercase() }
//                        )
//                    },
//                    navigationIcon = {
//                        IconButton(
//                            onClick = {
//                                corrutinaDrawer.launch { estadoDrawer.open() }
//                            }
//                        ) {
//                            Icon(
//                                imageVector = Icons.Filled.Menu,
//                                contentDescription = "Abrir menú"
//                            )
//                        }
//                    }
//                )
                LevelUpTopBar(
                    searchText = estadoUi.buscarConsulta,
                    onSearchTextChange = { viewModel.onBuscarConsultaChange(it) },
                    onMenuClick = { corrutinaDrawer.launch { estadoDrawer.open() } },
                    onCartClick = {
                        viewModel.onDrawerItemClick(Screen.Carrito)
                        corrutinaDrawer.launch { estadoDrawer.close() }
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
//                when (estadoUi.drawer.selected) {
//                    Screen.Home -> HomeScreen(
//                        onIrAlCarrito = {
//                            viewModel.onDrawerItemClick(Screen.Carrito)
//                        }
//                    )
//                    Screen.Catalogo -> CatalogoScreen()
//                    Screen.Carrito -> CarritoScreen()
//                    else -> { }
                LevelUpNavHost(
                    navController = navController,
                    viewModel = viewModel,
                    estadoUi = estadoUi
                )
            }
        }
    }
}


@Composable
fun LevelUpNavHost(
    navController: NavHostController,
    viewModel: MainViewModel,
    estadoUi: AppUiState
){
    // Leer el estado desde el ViewModel
//    val estadoUi by viewModel.estadoUi.collectAsState()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                productos = viewModel.productos,
                buscarConsulta = estadoUi.buscarConsulta,
                hayItemsCarrito = estadoUi.cartItems.isNotEmpty(),
                onIrAlCarrito = {
                    viewModel.onDrawerItemClick(Screen.Carrito)
                }
            )
        }

        composable(Screen.Catalogo.route) {
            CatalogoScreen(
                productos = viewModel.filtrarProductos(),
                cartItems = estadoUi.cartItems,
                onAgregarAlCarrito = { producto ->
                    viewModel.agregarAlCarrito(producto)
                },
                onQuitarUno = { producto ->
                    viewModel.quitarUnoDelCarrito(producto)
                }
            )
        }

        composable(Screen.Carrito.route) {
            CarritoScreen(
                items = estadoUi.cartItems,
                total = viewModel.calcularTotal(),
                onQuitarUno = { viewModel.quitarUnoDelCarrito(it) },
                onEliminar = { viewModel.eliminarDelCarrito(it) // borra todas las unidades
                },
                onPagar = {
                    viewModel.limpiarCarrito()
                }
            )
        }
    }
}