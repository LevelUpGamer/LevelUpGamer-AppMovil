package com.example.levelupgamer.ui

import androidx.compose.material3.MaterialTheme
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.levelupgamer.R
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.ui.graphics.Brush
import com.example.levelupgamer.ui.theme.LevelUpPurplePrimary
import com.example.levelupgamer.ui.theme.LevelUpWhite
import com.example.levelupgamer.viewmodel.AuthViewModel

//
//private const val PREFS_NAME = "MyGamingPrefs"
//private const val KEY_USERNAME = "username"
//private const val KEY_PASSWORD = "password"

@Composable
fun LoginScreen(
    authViewModel: AuthViewModel,
    onLoggeado: () -> Unit,
    onRegistrarse: () -> Unit
) {
    val contexto = LocalContext.current

    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }

    // Leemos el estado expuesto por AuthViewModel
    val uiState by authViewModel.uiState.collectAsState()

    // Cuando el login sea exitoso, navegamos
    LaunchedEffect(uiState.isLoggedIn) {
        if (uiState.isLoggedIn) {
            Toast.makeText(
                contexto,
                "Bienvenid@ $correo",
                Toast.LENGTH_SHORT
            ).show()
            onLoggeado()
        }
    }

    GamerGradientBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo
            Image(
                painter = painterResource(id = R.drawable.ic_levelupgamer),
                contentDescription = "Logo Level Up Gamer",
                modifier = Modifier
                    .size(150.dp)
                    .padding(bottom = 16.dp)
            )

            Text(
                text = "Inicia sesión",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = correo,
                onValueChange = { correo = it },
                label = { Text("Correo") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = contrasena,
                onValueChange = { contrasena = it },
                label = { Text("Contraseña") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth()
            )

            // Error que venga del backend
            uiState.error?.let { mensaje ->
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = mensaje,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (correo.isBlank() || contrasena.isBlank()) {
                        Toast.makeText(
                            contexto,
                            "Debe completar correo y contraseña.",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        // Aquí llamamos a la API via ViewModel
                        authViewModel.login(correo, contrasena)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
//                    containerColor = MaterialTheme.colorScheme.primary,
//                    contentColor = MaterialTheme.colorScheme.onPrimary
                    containerColor = LevelUpPurplePrimary,
                    contentColor = LevelUpWhite
                )
            ) {
                Text(if (uiState.estaCargando) "Ingresando..." else "Iniciar sesión")
            }

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(onClick = onRegistrarse) {
                Text(
                    text = "¿No tienes cuenta? Regístrate",
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

@Composable
fun RegistroScreen(
    authViewModel: AuthViewModel,
    onRegistroExitoso: () -> Unit
) {
    val contexto = LocalContext.current

    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var confirmaPass by remember { mutableStateOf("") }
    var errorLocal by remember { mutableStateOf<String?>(null) }

    val uiState by authViewModel.uiState.collectAsState()

    // Cuando el registro sea exitoso, volvemos al Login
    LaunchedEffect(uiState.registroExitoso) {
        if (uiState.registroExitoso) {
            Toast.makeText(
                contexto,
                "Usuario registrado correctamente",
                Toast.LENGTH_SHORT
            ).show()
            authViewModel.consumirRegistroExitoso()
            onRegistroExitoso()
        }
    }

    GamerGradientBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_levelupgamer),
                contentDescription = "Logo Level Up Gamer",
                modifier = Modifier
                    .size(150.dp)
                    .padding(bottom = 16.dp)
            )

            Text(
                text = "Regístrate",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = correo,
                onValueChange = { correo = it },
                label = { Text("Correo") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = contrasena,
                onValueChange = { contrasena = it },
                label = { Text("Contraseña") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = confirmaPass,
                onValueChange = { confirmaPass = it },
                label = { Text("Confirmar contraseña") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth()
            )

            // Error de validación local
            errorLocal?.let { mensaje ->
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = mensaje,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp
                )
            }

            // Error que venga del backend
            uiState.error?.let { mensaje ->
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = mensaje,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val correoRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
                    val contrasenaRegex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&.#^_-]).{8,}$")

                    when {
                        correo.isBlank() || contrasena.isBlank() || confirmaPass.isBlank() ->
                            errorLocal = "Debe completar todos los campos."

                        !correoRegex.matches(correo) ->
                            errorLocal = "Ingrese un correo válido (ej: nombre@correo.com)."

                        !contrasenaRegex.matches(contrasena) ->
                            errorLocal = "La contraseña debe tener mínimo 8 caracteres, con mayúscula, minúscula, número y carácter especial."

                        contrasena != confirmaPass ->
                            errorLocal = "Las contraseñas no coinciden."

                        else -> {
                            errorLocal = null
                            authViewModel.registro(correo, contrasena)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(if (uiState.estaCargando) "Registrando..." else "Enviar")
            }
        }
    }
}

@Composable
fun GamerGradientBackground(
    content: @Composable BoxScope.() -> Unit
) {
    val colors = listOf(
        MaterialTheme.colorScheme.background,
        MaterialTheme.colorScheme.surface
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = colors
                )
            )
    ) {
        content()
    }
}