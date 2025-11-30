package com.example.levelupgamer.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LevelUpDarkColorScheme = darkColorScheme(
    primary = LevelUpPurplePrimary,      // Color principal
    secondary = LevelUpGreenAccent,      // Color verde de acento
    background = LevelUpPurpleDark,      // Fondo más oscuro
    surface = LevelUpGamerBgLight,       // Fondo para Cards y barras
    onPrimary = LevelUpWhite,
    onSecondary = LevelUpBlack,          // El texto sobre el verde
    onBackground = LevelUpWhite,
    onSurface = LevelUpWhite,
    error = LevelUpAccentLight           // Color de error
)

@Composable
fun LevelUpGamerTheme(
    darkTheme: Boolean = true,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = LevelUpDarkColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}