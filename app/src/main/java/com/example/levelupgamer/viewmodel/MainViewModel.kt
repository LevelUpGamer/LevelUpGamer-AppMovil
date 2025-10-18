package com.example.levelupgamer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelupgamer.model.navigation.NavigationEvent
import com.example.levelupgamer.model.navigation.Screen
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

//Menú
data class DrawerUiState(
    val open: Boolean = false,
    val selected: Screen = Screen.Home
)

class MainViewModel : ViewModel() {

    // estado de UI --> true = abierto, false = cerrado
    private val _drawerAbierto = MutableStateFlow(DrawerUiState())
    val drawerAbierto = _drawerAbierto

    //eventos de navegación
    private val _eventos = MutableSharedFlow<NavigationEvent>()
    val eventos = _eventos.asSharedFlow()

    fun estadoDrawer(open: Boolean){
        _drawerAbierto.value = _drawerAbierto.value.copy(open = open)
    }

    fun onDrawerItemClick(dest: Screen){
        _drawerAbierto.value = _drawerAbierto.value.copy(selected = dest, open = false)
        viewModelScope.launch{
            _eventos.emit(NavigationEvent.NavigateTo(dest))
        }
    }

    fun retorno(){
        viewModelScope.launch {
            _eventos.emit(NavigationEvent.PopBackStack)
        }
    }

}