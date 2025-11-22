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
import androidx.compose.ui.res.colorResource


private const val PREFS_NAME = "MyGamingPrefs"
private const val KEY_USERNAME = "username"
private const val KEY_PASSWORD = "password"

@Composable
fun LoginScreen(
    onLoggeado: () -> Unit,
    onRegistrarse: () -> Unit
) {
    val contexto = LocalContext.current

    var usuario by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    GamerGradientBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            //Logo
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
//                color = colorResource(id = R.color.white),
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = usuario,
                onValueChange = { usuario = it },
                label = { Text("Usuario") },
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

            //Mensaje de error si existe
            error?.let { mensaje ->
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = mensaje,
                    //color = colorResource(id = R.color.accent_light),
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val prefs = contexto.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                    val usuarioGuardado = prefs.getString(KEY_USERNAME, null)
                    val passGuardada = prefs.getString(KEY_PASSWORD, null)

                    if (usuario == usuarioGuardado &&
                        contrasena == passGuardada &&
                        usuarioGuardado != null &&
                        passGuardada != null
                    ) {
                        error = null
                        Toast.makeText(
                            contexto,
                            "Bienvenid@ $usuario",
                            Toast.LENGTH_SHORT
                        ).show()
                        onLoggeado()
                    } else {
                        error = "Usuario o contraseña incorrectos. Inténtalo de nuevo."
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    //containerColor = colorResource(id = R.color.purple_primary),
                    containerColor = MaterialTheme.colorScheme.primary,
                    //contentColor = colorResource(id = R.color.white)
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text("Iniciar sesión")
            }

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(onClick = onRegistrarse) {
                Text(
                    text = "¿No tienes cuenta? Regístrate",
                    //color = colorResource(id = R.color.white)
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

@Composable
fun RegistroScreen(
    onRegistroExitoso: () -> Unit
) {
    val contexto = LocalContext.current

    var usuario by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var confirmaPass by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

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
                //color = colorResource(id = R.color.white),
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = usuario,
                onValueChange = { usuario = it },
                label = { Text("Usuario") },
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

            //Mensaje de error si existe
            error?.let { mensaje ->
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = mensaje,
                    //color = colorResource(id = R.color.accent_light),
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    when {
                        usuario.isBlank() || contrasena.isBlank() || confirmaPass.isBlank() ->
                            error = "Debe completar todos los campos."

                        contrasena != confirmaPass ->
                            error = "Las contraseñan no coinciden."

                        else -> {
                            val prefs =
                                contexto.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                            prefs.edit()
                                .putString(KEY_USERNAME, usuario)
                                .putString(KEY_PASSWORD, contrasena)
                                .apply()

                            error = null
                            Toast.makeText(
                                contexto,
                                "Usuario registrado correctamente",
                                Toast.LENGTH_SHORT
                            ).show()
                            onRegistroExitoso()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    //containerColor = colorResource(id = R.color.purple_primary),
                    containerColor = MaterialTheme.colorScheme.primary,
                    //contentColor = colorResource(id = R.color.white)
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text("Enviar")
            }
        }
    }
}

@Composable
fun GamerGradientBackground(
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        colorResource(id = R.color.gamer_bg_dark),
                        colorResource(id = R.color.gamer_bg_light)
                    )
                )
            )
    ) {
        content()
    }
}
