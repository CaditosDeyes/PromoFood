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
        textCorreoElectronico=findViewById(R.id.textCorreoElectronico)
        textContrasenia=findViewById(R.id.textContrasenia)
    }
    fun clickBtonIniciarSesion(view:View){
        val correoElectronico = textCorreoElectronico?.text.toString()
        val contrasenia = textContrasenia?.text.toString()
        val queue= Volley.newRequestQueue(this)
        val url="http://192.168.50.134/PromoFoodMYSQL/loginRestaurante.php?correoElectronico=$correoElectronico&contrasenia=$contrasenia"
        val jsonObjectRequest= JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                var intent= Intent(this, SesionRestauranteIniciadaActivity::class.java)
                startActivity(intent)
            }, Response.ErrorListener { error ->
                //Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()
                Toast.makeText(this, "No existe ese usuario", Toast.LENGTH_LONG).show()
            }
        )
        queue.add(jsonObjectRequest)
    }

    fun clickBtnRegresar(view: View){
        var intent= Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}