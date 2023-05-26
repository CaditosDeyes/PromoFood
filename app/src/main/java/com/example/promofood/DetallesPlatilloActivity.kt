package com.example.promofood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class DetallesPlatilloActivity : AppCompatActivity() {
    var id=""
    var precio=0.0
    var idPlatillo=""
    var resumen=""
    var idCliente=""
    var idRestaurante=""
    var nombre=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_platillo)
        id = intent.getStringExtra("id").toString()
        idCliente=intent.getStringExtra("idCliente").toString()
        idRestaurante= intent.getStringExtra("idRestaurante").toString()
        llenarTitulos()
    }
    fun llenarTitulos(){
        var queue= Volley.newRequestQueue(this)
        val ID=id
        resumen=""
        val url="https://promofood.000webhostapp.com/PromoFoodMYSQL/mostrarPlatilloID.php?id=$ID"
        var jsonObjectRequest= JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                val textTituloPlatillo=findViewById<View>(R.id.textTituloPlatillo) as TextView
                val textPrecioPlatilloBD=findViewById<View>(R.id.textPrecioPlatilloBD) as TextView
                val textIngredientesPlatilloBD=findViewById<View>(R.id.textIngredientesPlatilloBD) as TextView
                nombre=response.getString("nombre")
                var precioString=response.getString("precio")
                precio=precioString.toDouble()
                textTituloPlatillo.text=nombre
                textPrecioPlatilloBD.text=precioString
                textIngredientesPlatilloBD.text=response.getString("ingredientes")
                resumen="Platillo: "+nombre
                resumen=resumen+"\nPrecio: "+precioString
                idPlatillo=response.getString("id")
            }, Response.ErrorListener { error ->
                Toast.makeText(this, "No se encontró informacion", Toast.LENGTH_LONG).show()
            }
        )
        queue.add(jsonObjectRequest)
    }
    fun clickBtnAgregarCarrito(view:View){
        agregarAResenia()
        val url="https://promofood.000webhostapp.com/PromoFoodMYSQL/insertarCarrito.php"
        var queue= Volley.newRequestQueue(this)
        var resultadoPost= object : StringRequest(Request.Method.POST, url,
            Response.Listener<String>{ response->
                Toast.makeText(this, "Platillo agregado a carrito", Toast.LENGTH_LONG).show()
            }, Response.ErrorListener { error ->
                Toast.makeText(this, "Error al agregar a carrito", Toast.LENGTH_LONG).show()
            }
        ){
            override fun getParams(): MutableMap<String, String>? {
                val parametros=HashMap<String, String>()
                parametros.put("idCliente", idCliente)
                parametros.put("idRestaurantePerteneciente", idRestaurante)
                parametros.put("resumen", resumen)
                parametros.put("precioIndividual", precio.toString())
                return parametros
            }
        }
        queue.add(resultadoPost)
    }
    fun clickBtnVerCarrito(view:View){
        var intent= Intent(this, DetallesCarritoActivity::class.java)
        intent.putExtra("idCliente", idCliente)
        intent.putExtra("idRestaurante", idRestaurante)
        startActivity(intent)
    }

    fun agregarAResenia(){
        val url="https://promofood.000webhostapp.com/PromoFoodMYSQL/insertarResenia.php"
        var queue= Volley.newRequestQueue(this)
        var resultadoPost= object : StringRequest(Request.Method.POST, url,
            Response.Listener<String>{ response->
            }, Response.ErrorListener { error ->
            }
        ){
            override fun getParams(): MutableMap<String, String>? {
                val parametros=HashMap<String, String>()
                parametros.put("idClienteSolicitado", idCliente)
                parametros.put("idPlatillo", idPlatillo)
                parametros.put("idBebida", "0")
                parametros.put("nombrePlatilloBebida", nombre)
                parametros.put("descripcion", "algo")
                parametros.put("puntuacion", "0")
                parametros.put("estatus", "Enviando")
                return parametros
            }
        }
        queue.add(resultadoPost)
    }

    fun clickBtnVerResenias(view: View){
        var intent= Intent(this, VerReseniasPlatilloActivity::class.java)
        intent.putExtra("idPlatillo", idPlatillo)
        startActivity(intent)
    }
}