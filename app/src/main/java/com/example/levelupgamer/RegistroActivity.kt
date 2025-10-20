package com.example.levelupgamer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences


class RegistroActivity : AppCompatActivity() {

    // Nombres de las claves para SharedPreferences
    private val PREFS_NAME = "MyGamingPrefs"
    private val KEY_USERNAME = "username"
    private val KEY_PASSWORD = "password"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




        setContentView(R.layout.layout_registro)


        val etUsuario = findViewById<EditText>(R.id.et_usuario)
        val etContrasena = findViewById<EditText>(R.id.et_contrasena)
        val btnSend = findViewById<Button>(R.id.btn_send)

        // 1. Configurar el Listener para el botón de registro
        btnSend.setOnClickListener {
            val usuario = etUsuario.text.toString().trim()
            val contrasena = etContrasena.text.toString().trim()

            // 2. Validar que los campos no estén vacíos
            if (usuario.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(this, "Por favor, completa ambos campos.", Toast.LENGTH_SHORT).show()
            } else {
                // 3. Guardar las credenciales
                guardarCredenciales(usuario, contrasena)

                // Mensaje de éxito
                Toast.makeText(this, "¡Registro exitoso! Datos guardados localmente.", Toast.LENGTH_LONG).show()

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)

                // 3. Cerrar RegistroActivity para que no se pueda devolver
                finish()
            }
        }
    }

    /**
     * Función para guardar el nombre de usuario y la contraseña usando SharedPreferences.
     */
    private fun guardarCredenciales(usuario: String, contrasena: String) {
        // Obtenemos una instancia de SharedPreferences
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        // Obtenemos un Editor para modificar los datos
        val editor = sharedPreferences.edit()

        // Guardamos las claves y valores
        editor.putString(KEY_USERNAME, usuario)
        editor.putString(KEY_PASSWORD, contrasena)

        // Aplicamos los cambios de forma asíncrona (commit() lo haría de forma síncrona)
        editor.apply()
    }
}
