package com.example.levelupgamer.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.levelupgamer.ui.RegistroScreen
import com.example.levelupgamer.ui.theme.LevelUpGamerTheme
import com.example.levelupgamer.viewmodel.AuthViewModel

class RegistroActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent{
            LevelUpGamerTheme {
                RegistroScreen(
                    authViewModel = authViewModel,
                    onRegistroExitoso = {
                        //Después de registrarse, volvemos al Login
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    }
                )
            }
        }
    }
}