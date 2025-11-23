package com.example.levelupgamer.view

import android.content.Intent
import android.os.Bundle
import androidx.core.app.ComponentActivity

class SplashActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Simulación de espera
        window.decorView.postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, 1000)
    }
}