package com.example.levelupgamer

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import android.os.Handler
import android.os.Looper

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
