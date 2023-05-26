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
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class RegistroClienteActivity : AppCompatActivity() {
    var textNombre: EditText?=null
    var textCorreoElectronico: EditText?=null
    var textContrasenia: EditText?=null
    var textTelefono: EditText?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_cliente)
        textNombre=findViewById(R.id.textNombre)
        textCorreoElectronico=findViewById(R.id.textBuscarRestaurante)
        textContrasenia=findViewById(R.id.textContrasenia)
        textTelefono=findViewById(R.id.textTelefono)
    }
    fun clickBtnInsertar(view: View){
        val url="https://promofood.000webhostapp.com/PromoFoodMYSQL/insertarCliente.php"
        var queue= Volley.newRequestQueue(this)
        var nombre=textNombre?.text.toString()
        var correoElectrnico=textCorreoElectronico?.text.toString()
        var contrasenia=textContrasenia?.text.toString()
        var telefono=textTelefono?.text.toString()
        if(nombre=="" || correoElectrnico=="" || contrasenia=="" || telefono==""){
            Toast.makeText(this, "Llene todo los campos para realizar el registro", Toast.LENGTH_LONG).show()
        }else{
            var resultadoPost= object : StringRequest(Request.Method.POST, url,
                Response.Listener<String>{ response->
                    Toast.makeText(this, "Usuario registrado exitosamente", Toast.LENGTH_LONG).show()
                    obtenerIdCliente()
                }, Response.ErrorListener { error ->
                    Toast.makeText(this, "Error $error", Toast.LENGTH_LONG).show()
                }
            ){
                override fun getParams(): MutableMap<String, String>? {
                    val parametros=HashMap<String, String>()
                    parametros.put("nombre", nombre)
                    parametros.put("correoElectronico", correoElectrnico)
                    parametros.put("contrasenia", contrasenia)
                    parametros.put("numeroTelefono", telefono)
                    return parametros
                }
            }
            queue.add(resultadoPost)
        }
    }
    fun obtenerIdCliente(){
        val correoElectronico = textCorreoElectronico?.text.toString()
        val contrasenia = textContrasenia?.text.toString()
        val queue= Volley.newRequestQueue(this)
        val url="https://promofood.000webhostapp.com/PromoFoodMYSQL/LoginCliente.php?correoElectronico=$correoElectronico&contrasenia=$contrasenia"
        val jsonObjectRequest= JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                val idCliente=response.getString("id")
                var intent= Intent(this, SesionClienteIniciadaActivity::class.java)
                intent.putExtra("idCliente", idCliente)
                startActivity(intent)
            }, Response.ErrorListener { error ->
                Toast.makeText(this, "No existe ese usuario", Toast.LENGTH_LONG).show()
            }
        )
        queue.add(jsonObjectRequest)
        textNombre?.setText("")
        textCorreoElectronico?.setText("")
        textContrasenia?.setText("")
        textTelefono?.setText("")
    }

    fun clickBtnRegresar(view: View){
        finish()
    }
}