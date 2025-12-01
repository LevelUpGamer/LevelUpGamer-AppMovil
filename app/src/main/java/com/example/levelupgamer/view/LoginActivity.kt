package com.example.levelupgamer.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.levelupgamer.ui.LoginScreen
import com.example.levelupgamer.ui.theme.LevelUpGamerTheme
import com.example.levelupgamer.viewmodel.AuthViewModel

class LoginActivity : ComponentActivity(){

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent{
            LevelUpGamerTheme {
                LoginScreen(
                    authViewModel = authViewModel,
                    onLoggeado = {
                        // Cuando el Login es correcto, vamos al DrawerActivity
                        startActivity(Intent(this, DrawerActivity::class.java))
                        finish()
                    },
                    onRegistrarse = {
                        startActivity(Intent(this, RegistroActivity::class.java))
                    }
                )
            }
        }
    }
}