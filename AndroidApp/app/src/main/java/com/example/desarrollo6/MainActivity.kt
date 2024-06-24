package com.example.desarrollo6

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject
import java.nio.charset.Charset

class PantallaConsultar : AppCompatActivity() {

    // DECLARACION DE LOS CAMPOS DE TEXTO Y BOTONES
    private lateinit var campo_cedula: EditText
    private lateinit var campo_nombres: EditText
    private lateinit var campo_apellidos: EditText
    private lateinit var campo_direccion: EditText
    private lateinit var campo_telefono: EditText
    private lateinit var campo_correo: EditText
    private lateinit var boton_buscar: Button
    private lateinit var boton_limpiar: Button
    private lateinit var boton_crear: ImageButton
    private lateinit var boton_actualizar: ImageButton
    private lateinit var boton_eliminar: ImageButton

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        // LLAMADA AL METODO ONCREATE DEL PADRE
        super.onCreate(savedInstanceState)
        // ESTABLECER EL CONTENIDO DE LA VISTA
        setContentView(R.layout.activity_main)

        // INICIALIZACION DE LOS CAMPOS DE TEXTO Y BOTONES
        campo_cedula = findViewById(R.id.campo_cedula)
        campo_nombres = findViewById(R.id.campo_nombres)
        campo_apellidos = findViewById(R.id.campo_apellidos)
        campo_direccion = findViewById(R.id.campo_direccion)
        campo_telefono = findViewById(R.id.campo_telefono)
        campo_correo = findViewById(R.id.campo_correo)
        boton_buscar = findViewById(R.id.boton_buscar)
        boton_limpiar = findViewById(R.id.boton_limpiar)
        boton_crear = findViewById(R.id.boton_crear)
        boton_actualizar = findViewById(R.id.boton_actualizar)
        boton_eliminar = findViewById(R.id.boton_eliminar)

        // ASIGNACION DE FUNCIONES A LOS BOTONES
        boton_buscar.setOnClickListener {
            Log.d("MainActivity", "BUSCANDO DATOS")
            val cedula = campo_cedula.text.toString()

            // Realizar la consulta con la cédula ingresada
            realizarConsulta(cedula)
        }
        
        boton_limpiar.setOnClickListener() {
            // LIMPIAR LOS CAMPOS DE TEXTO
            campo_cedula.setText("")
            campo_nombres.setText("")
            campo_apellidos.setText("")
            campo_direccion.setText("")
            campo_telefono.setText("")
            campo_correo.setText("")
        }

        boton_crear.setOnClickListener() {
            // OBTENER LOS DATOS INGRESADOS EN LOS CAMPOS DE TEXTO
            val cedula = campo_cedula.text.toString()
            val nombres = campo_nombres.text.toString()
            val apellidos = campo_apellidos.text.toString()
            val direccion = campo_direccion.text.toString()
            val telefono = campo_telefono.text.toString()
            val correo = campo_correo.text.toString()


            // REALIZAR LA ACTUALIZACION DE DATOS
            realizarInsercion(
                cedula,
                nombres,
                apellidos,
                direccion,
                telefono,
                correo
            )
        }

        boton_actualizar.setOnClickListener() {

            // OBTENER LOS DATOS INGRESADOS EN LOS CAMPOS DE TEXTO
            val cedula = campo_cedula.text.toString()
            val nombres = campo_nombres.text.toString()
            val apellidos = campo_apellidos.text.toString()
            val direccion = campo_direccion.text.toString()
            val telefono = campo_telefono.text.toString()
            val correo = campo_correo.text.toString()

            //SEPARAR DATOS
            val nuevoNombre = nombres.split(" ")
            val nuevoApellido = apellidos.split(" ")

            val nombre1 = nuevoNombre.getOrNull(0)?.trim() ?: ""
            val nombre2 = nuevoNombre.getOrNull(1)?.trim() ?: ""

            val apellido1 = nuevoApellido.getOrNull(0)?.trim() ?: ""
            val apellido2 = nuevoApellido.getOrNull(1)?.trim() ?: ""


            // REALIZAR LA ACTUALIZACION DE DATOS
            realizarActualizacion(
                cedula,
                nombre1,
                nombre2,
                apellido1,
                apellido2,
                direccion,
                telefono,
                correo
            )
        }

        boton_eliminar.setOnClickListener() {
            // OBTENER LA CEDULA INGRESADA
            val cedula = campo_cedula.text.toString()
            // REALIZAR LA ELIMINACION DE DATOS
            realizarEliminacion(cedula)
        }
    }

    // FUNCION PARA REALIZAR CONSULTA DE DATOS
    private fun realizarConsulta(cedula: String) {
        val url = "http://10.0.2.2/Android/consulta_mostrar.php?cedula=$cedula"

        val stringRequest = object : StringRequest(
            Method.POST,
            url,
            Response.Listener<String> { response ->
                try {
                    Log.d("MainActivity", "Dato obtenido: " + response)
                    val datos = response.split(":", ",") // Separar los datos por coma

                    if (datos.size >= 16) {
                        // ASIGNACION DE LOS DATOS A LOS CAMPOS DE TEXTO
                        campo_cedula.setText(datos[1].trim().replace("\"", ""))
                        campo_nombres.setText(
                            datos[3].trim().replace("\"", "") + " " + datos[5].trim()
                                .replace("\"", "")
                        )
                        campo_apellidos.setText(
                            datos[7].trim().replace("\"", "") + " " + datos[9].trim()
                                .replace("\"", "")
                        )
                        campo_direccion.setText(datos[11].trim().replace("\"", ""))
                        campo_telefono.setText(datos[13].trim().replace("\"", ""))
                        campo_correo.setText(datos[15].trim().replace("\"", "").replace("}", ""))

                    } else {
                        // Mostrar error si el formato de respuesta no es válido
                        showErrorSnackbar("Error en el formato de respuesta del servidor")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e("MainActivity", "Error al procesar la respuesta: ${e.message}")
                }
            },
            Response.ErrorListener { error ->
                // Maneja del error
                showErrorSnackbar("No se ha podido buscar")
                Log.e("MainActivity", "Error de Volley: ${error.message}", error)
            }) {

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/x-www-form-urlencoded"
                return headers
            }
        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }

    // FUNCION PARA MOSTRAR UN MENSAJE TOAST
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    // FUNCION PARA MOSTRAR UN MENSAJE SNACKBAR DE ERROR
    private fun showErrorSnackbar(message: String) {
        val rootView = findViewById(android.R.id.content) as? View
        rootView?.let {
            Snackbar.make(it, message, Snackbar.LENGTH_SHORT).show()
        }
    }

    // FUNCION PARA REALIZAR INSERCION DE DATOS
    private fun realizarInsercion(
        cedula: String,
        nombres: String,
        apellidos: String,
        direccion: String,
        telefono: String,
        email: String
    ) {
        val url = "http://10.0.2.2/Android/consulta_insertar.php"

        val stringRequest = object : StringRequest(
            Method.POST,
            url,
            Response.Listener { response ->
                showToast("DATOS INSERTADOS CORRECTAMENTE")
                Log.d("MainActivity", "Response received: $response")
            },
            Response.ErrorListener { error ->
                Log.e("MainActivity", "Error de Volley: ${error.message}", error)
                showErrorSnackbar("ERROR AL INSERTAR DATOS")
            }) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["cedula"] = cedula
                params["nombre"] = nombres.split(" ")[0] // Primer nombre
                params["segundoNombre"] = if (nombres.split(" ").size > 1) nombres.split(" ")[1] else "" // Segundo nombre
                params["primerApellido"] = apellidos.split(" ")[0] // Primer apellido
                params["segundoApellido"] = if (apellidos.split(" ").size > 1) apellidos.split(" ")[1] else "" // Segundo apellido
                params["direccion"] = direccion
                params["telefono"] = telefono
                params["email"] = email
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }

    // FUNCION PARA REALIZAR ACTUALIZACION DE DATOS
    private fun realizarActualizacion(
        cedula: String,
        nombre1: String,
        nombre2: String,
        apellido1: String,
        apellido2: String,
        direccion: String,
        telefono: String,
        email: String
    ) {
        val url = "http://10.0.2.2/Android/consulta_actualizar.php"

        val stringRequest = object : StringRequest(
            Method.POST,
            url,
            Response.Listener { response ->
                try {
                    Log.d("MainActivity", "Respuesta recibida: $response")
                    showToast("DATOS ACTUALIZADOS EXISTOSAMENTE")

                    if (response.contains("true")) {
                        Log.d("MainActivity", "Datos actualizados correctamente")
                    } else {
                        Log.d("MainActivity", "Fallo al actualizar los datos")
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e("MainActivity", "Error al procesar la respuesta: ${e.message}")
                }
            },
            Response.ErrorListener { error ->
                Log.e("MainActivity", "Error de Volley: ${error.message}", error)
                showErrorSnackbar("NO SE HA PODIDO ACTUALIZAR LOS DATOS")
            }) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["cedula"] = cedula
                params["nombre1"] = nombre1
                params["nombre2"] = nombre2
                params["apellido1"] = apellido1
                params["apellido2"] = apellido2
                params["direccion"] = direccion
                params["telefono"] = telefono
                params["email"] = email
                return params
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/x-www-form-urlencoded"
                return headers
            }
        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }

    // FUNCION PARA REALIZAR ELIMINACION DE DATOS
    private fun realizarEliminacion(cedula: String) {
        val url = "http://10.0.2.2/Android/consulta_eliminar.php"

        val stringRequest = object : StringRequest(
            Method.POST,
            url,
            Response.Listener { response ->
                showToast("DATOS ELIMINADOS CORRECTAMENTE")
                Log.d("MainActivity", "Response received: $response")
            },
            Response.ErrorListener { error ->
                Log.e("MainActivity", "Error de Volley: ${error.message}", error)
                showErrorSnackbar("ERROR AL ELIMINAR LOS DATOS")
            }) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["cedula"] = cedula
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }
}
