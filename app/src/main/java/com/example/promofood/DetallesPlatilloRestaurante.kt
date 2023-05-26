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

class DetallesPlatilloRestaurante : AppCompatActivity() {
    var id=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_platillo_restaurante)
        id = intent.getStringExtra("id").toString()
        llenarTitulos()
    }

    fun llenarTitulos(){
        var queue= Volley.newRequestQueue(this)
        val ID=id
        val url="https://promofood.000webhostapp.com/PromoFoodMYSQL/mostrarPlatilloID.php?id=$ID"
        var jsonObjectRequest= JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                val textTituloPlatillo=findViewById<View>(R.id.textTituloPlatillo) as TextView
                val textPrecioPlatilloBD=findViewById<View>(R.id.textPrecioPlatilloBD) as TextView
                val textIngredientesPlatilloBD=findViewById<View>(R.id.textIngredientesPlatilloBD) as TextView
                var nombre=response.getString("nombre")
                var precioString=response.getString("precio")
                textTituloPlatillo.text=nombre
                textPrecioPlatilloBD.text=precioString
                textIngredientesPlatilloBD.text=response.getString("ingredientes")
            }, Response.ErrorListener { error ->
                Toast.makeText(this, "No se encontró el platillo", Toast.LENGTH_LONG).show()
            }
        )
        queue.add(jsonObjectRequest)
    }
}