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

class DetallesBebidaActivity : AppCompatActivity() {
    var id=""
    var idBebida=""
    var precio=0.0
    var resumen=""
    var idCliente=""
    var idRestaurante=""
    var nombre=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_bebida)
        val ID = intent.getStringExtra("id").toString()
        id=ID
        idCliente=intent.getStringExtra("idCliente").toString()
        idRestaurante= intent.getStringExtra("idRestaurante").toString()
        llenarTitulos()
    }
    fun llenarTitulos(){
        var queue= Volley.newRequestQueue(this)
        val ID=id
        resumen=""
        val url="https://promofood.000webhostapp.com/PromoFoodMYSQL/mostrarBebidaID.php?id=$ID"
        var jsonObjectRequest= JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                val textTituloBebida=findViewById<View>(R.id.textTituloBebida) as TextView
                val textPrecioBebidaBD=findViewById<View>(R.id.textPrecioBebidaBD) as TextView
                val textIngredientesBebidaBD=findViewById<View>(R.id.textIngredientesBebidaBD) as TextView
                nombre=response.getString("nombre")
                var precioString=response.getString("precio")
                precio=precioString.toDouble()
                textTituloBebida.text=nombre
                textPrecioBebidaBD.text=precioString
                textIngredientesBebidaBD.text=response.getString("ingredientes")
                resumen="Bebida: "+nombre
                resumen=resumen+"\nPrecio: "+precioString
                idBebida=response.getString("id")
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
                Toast.makeText(this, "Bebida agregado a carrito", Toast.LENGTH_LONG).show()
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
                parametros.put("idPlatillo", "0")
                parametros.put("idBebida", idBebida)
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
        var intent= Intent(this, VerReseniasBebidaActivity::class.java)
        intent.putExtra("idBebida", idBebida)
        startActivity(intent)
    }
}