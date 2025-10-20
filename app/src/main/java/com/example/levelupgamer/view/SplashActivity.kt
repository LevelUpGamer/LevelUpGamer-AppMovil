package com.example.levelupgamer.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen()
        super.onCreate(savedInstanceState)


        Handler(Looper.getMainLooper()).postDelayed({
            // 3️⃣ Pasar al login
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, 2000)
    }
}