package com.example.promofood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class DetallesBebidaRestaurante : AppCompatActivity() {
    var id=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_bebida_restaurante)
        id = intent.getStringExtra("id").toString()
        llenarTitulos()
    }
    fun llenarTitulos(){
        var queue= Volley.newRequestQueue(this)
        val ID=id
        val url="https://promofood.000webhostapp.com/PromoFoodMYSQL/mostrarBebidaID.php?id=$ID"
        var jsonObjectRequest= JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                val textTituloBebida=findViewById<View>(R.id.textTituloBebida) as TextView
                val textPrecioBebidaBD=findViewById<View>(R.id.textPrecioBebidaBD) as TextView
                val textIngredientesBebidaBD=findViewById<View>(R.id.textIngredientesBebidaBD) as TextView
                var nombre=response.getString("nombre")
                var precioString=response.getString("precio")
                textTituloBebida.text=nombre
                textPrecioBebidaBD.text=precioString
                textIngredientesBebidaBD.text=response.getString("ingredientes")
            }, Response.ErrorListener { error ->
                Toast.makeText(this, "No se encontró la bebida", Toast.LENGTH_LONG).show()
            }
        )
        queue.add(jsonObjectRequest)
    }
}