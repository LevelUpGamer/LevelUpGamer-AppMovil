package com.example.levelupgamer

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.example.levelupgamer.model.navigation.Screen
import com.example.levelupgamer.ui.LevelUpDrawer
import com.example.levelupgamer.viewmodel.DrawerUiState
import com.example.levelupgamer.viewmodel.MainViewModel

class DrawerActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        setContent {
            var ui by remember {
                mutableStateOf(DrawerUiState(open = false, selected = Screen.Home))
            }

            LevelUpDrawer(
                estadoDrawer = ui,
                onSelect = { screen ->
                    when (screen) {
                        Screen.CerrarSesion -> {
                            //ir al Login y cerrar esra actividad
                            startActivity(Intent(this, LoginActivity::class.java))
                            finish()
                        }

                        else -> {
                            ui = ui.copy(selected = screen)
                        }
                    }
                },

                //aquí uso el conyenido de mis layouts
                contenido = {
                    when (ui.selected) {
                        Screen.Home -> HomeScreenStub()
                        Screen.Catalogo -> CatalogoScreenStub()
                        Screen.Carrito -> CarritoScreenStub()
                        Screen.CerrarSesion -> {}
                    }
                }
            )
        }
    }
}