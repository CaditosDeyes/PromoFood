package com.example.promofood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity() {
    var textNombre:EditText?=null
    var textCorreoElectronico:EditText?=null
    var textContrasenia:EditText?=null
    var textTelefono:EditText?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textNombre=findViewById(R.id.textNombre)
        textCorreoElectronico=findViewById(R.id.textCorreoElectronico)
        textContrasenia=findViewById(R.id.textContrasenia)
        textTelefono=findViewById(R.id.textTelefono)
    }
    fun clickBtnInsertar(view: View){
        val url="http://192.168.50.134/PromoFoodMYSQL/insertar.php"
        val queue= Volley.newRequestQueue(this)
        var nombre=textNombre?.text.toString()
        var correoElectrnico=textCorreoElectronico?.text.toString()
        var contrasenia=textContrasenia?.text.toString()
        var telefono=textTelefono?.text.toString()
        var resultadoPost= object : StringRequest(Request.Method.POST, url,
            Response.Listener<String>{ response->
                Toast.makeText(this, "Usuario insertado exitosamente", Toast.LENGTH_LONG).show()
            }, Response.ErrorListener { error ->
                    Toast.makeText(this, "Error $error", Toast.LENGTH_LONG).show()
                }
            ){
            override fun getParams(): MutableMap<String, String>? {
                val parametros=HashMap<String, String>()
                parametros.put("nombre", nombre)
                parametros.put("correoElectronico", correoElectrnico)
                parametros.put("contrasenia", contrasenia)
                parametros.put("telefono", telefono)
                return parametros
            }
        }
        queue.add(resultadoPost)
        textNombre?.setText("")
        textCorreoElectronico?.setText("")
        textContrasenia?.setText("")
        textTelefono?.setText("")
    }
    fun clickBtnVer(view:View){
        var textNombre:EditText=findViewById<EditText>(R.id.textConsultar)
        var nombre=textNombre.text.toString()
        var intent= Intent(this, MainActivity2::class.java)
        intent.putExtra("nombre", nombre)
        startActivity(intent)
        textNombre.setText("")
    }
}