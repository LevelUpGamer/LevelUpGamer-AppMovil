package com.example.levelupgamer.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.levelupgamer.view.MainActivity
import com.example.levelupgamer.R
import com.example.levelupgamer.view.RegistroActivity

class LoginActivity: AppCompatActivity() {
    // misma config de claves que en Registro
    private val PREFS_NAME = "MyGamingPrefs"
    private val KEY_USERNAME = "username"
    private val KEY_PASSWORD = "password"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_login)

        val btn: Button =findViewById(R.id.btn_registrarse)
        btn.setOnClickListener {
            val intent: Intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }



        val usuarioInput = findViewById<EditText>(R.id.usuarioInput)
        val campoPass = findViewById<EditText>(R.id.campoPass)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        //listener del btnLogin
        btnLogin.setOnClickListener {
            val usuario = usuarioInput.text.toString().trim()
            val contrasena = campoPass.text.toString().trim()

            //validando los campos
            if (usuario.isEmpty() || contrasena.isEmpty()){
                Toast.makeText(this, "Por favor, complete ambos campos.", Toast.LENGTH_SHORT).show()
            } else {
                if (verificarCredenciales(usuario, contrasena)) {
                    Toast.makeText(this, "Inicio de sesión exitoso.", Toast.LENGTH_SHORT).show()

                    // Ir a la pantalla ppal
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Usuario o contraseña incorrectos. Inténtelo de nuevo.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //verificación de credenciales guardadas con las que están en SharedPreferences
    private fun verificarCredenciales(usuario: String, contrasena: String): Boolean {
        val sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val usuarioGuardado = sharedPreferences.getString(KEY_USERNAME, null)
        val passGuardada = sharedPreferences.getString(KEY_PASSWORD, null)

        return (usuario == usuarioGuardado && contrasena == passGuardada)
    }
}