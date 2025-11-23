package com.example.levelupgamer.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.activity.ComponentActivity
import com.example.levelupgamer.R
import com.example.levelupgamer.ui.theme.LevelUpGamerTheme
import kotlinx.coroutines.delay
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class SplashActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen() // evita pantalla negra e ícono verde(?)

        super.onCreate(savedInstanceState)

        setContent {
            LevelUpGamerTheme {
                SplashScreen(
                    onFinish = {
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    }
                )
            }
        }
    }
}

@Composable
fun SplashScreen(onFinish: () -> Unit) {
    var startAnimation by remember { mutableStateOf(false) }

    // escala animada: inicia pequeña (0.7f) y crece a 1.2f
    val scale by animateFloatAsState(
        targetValue = if (startAnimation) 1.4f else 0.7f,
        animationSpec = tween(durationMillis = 1200),
        label = "logoScale"
    )

    LaunchedEffect(true) {
        startAnimation = true
        delay(1500)  // duración total antes de ir al Login
        onFinish()
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_levelupgamer),
            contentDescription = "Logo Level Up Gamer",
            modifier = Modifier
                .size(180.dp)
                .scale(scale)
        )
    }
}