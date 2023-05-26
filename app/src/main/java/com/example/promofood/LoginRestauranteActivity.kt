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

class LoginRestauranteActivity : AppCompatActivity() {
    var textCorreoElectronico: EditText?=null
    var textContrasenia: EditText?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_restaurante)
        textCorreoElectronico=findViewById(R.id.textBuscarRestaurante)
        textContrasenia=findViewById(R.id.textContrasenia)
    }
    fun clickBtonIniciarSesion(view:View){
        val correoElectronico = textCorreoElectronico?.text.toString()
        val contrasenia = textContrasenia?.text.toString()
        val queue= Volley.newRequestQueue(this)
        val url="https://promofood.000webhostapp.com/PromoFoodMYSQL/LoginRestaurante.php?correoElectronico=$correoElectronico&contrasenia=$contrasenia"
        if(correoElectronico=="" || contrasenia==""){
            Toast.makeText(this, "Llene todo los campos para iniciar sesión", Toast.LENGTH_LONG).show()
        }else{
            val jsonObjectRequest= JsonObjectRequest(
                Request.Method.GET, url, null,
                Response.Listener { response ->
                    val idRestaurante=response.getString("id")
                    var intent= Intent(this, SesionRestauranteIniciadaActivity::class.java)
                    intent.putExtra("id", idRestaurante)
                    startActivity(intent)
                }, Response.ErrorListener { error ->
                    Toast.makeText(this, "No existe ese usuario", Toast.LENGTH_LONG).show()
                }
            )
            queue.add(jsonObjectRequest)
        }
    }

    fun clickBtnRegresar(view: View){
        finish()
    }
}