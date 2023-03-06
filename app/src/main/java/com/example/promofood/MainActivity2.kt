package com.example.promofood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class MainActivity2 : AppCompatActivity() {
    var textNombre: EditText?=null
    var textCorreoElectronico: EditText?=null
    var textContrasenia: EditText?=null
    var textTelefono: EditText?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        textNombre=findViewById(R.id.textNombre)
        textCorreoElectronico=findViewById(R.id.textCorreoElectronico)
        textContrasenia=findViewById(R.id.textContrasenia)
        textTelefono=findViewById(R.id.textTelefono)
        val nombre = intent.getStringExtra("nombre").toString()
        val queue=Volley.newRequestQueue(this)
        val url="http://192.168.50.134/PromoFoodMYSQL/registro.php?nombre=$nombre"
        val jsonObjectRequest= JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                textNombre?.setText(response.getString("nombre"))
                textCorreoElectronico?.setText(response.getString("correoElectronico"))
                textContrasenia?.setText(response.getString("contrasenia"))
                textTelefono?.setText(response.getString("telefono"))
            }, Response.ErrorListener { error ->
                Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()
            }
        )
        queue.add(jsonObjectRequest)
        fun clickBtnRegresar(view: View){
            var intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}