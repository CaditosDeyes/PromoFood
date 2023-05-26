package com.example.promofood

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class ReseniarProductoActivity : AppCompatActivity() {
    var idCliente=""
    var idResenia=""
    var textCalificacion: EditText?=null
    var textDescripcion: EditText?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reseniar_producto)
        idCliente=intent.getStringExtra("idCliente").toString()
        idResenia=intent.getStringExtra("idResenia").toString()
        textCalificacion=findViewById(R.id.textCalificacion)
        textDescripcion=findViewById(R.id.textDescripcion)
        llenarTitulos()
    }
    fun llenarTitulos(){
        var queue= Volley.newRequestQueue(this)
        val ID_RESENIA = idResenia
        val url="https://promofood.000webhostapp.com/PromoFoodMYSQL/mostrarReseniaId.php?id=$ID_RESENIA"
        var jsonObjectRequest= JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                val TituloReseniarProducto=findViewById<View>(R.id.TituloReseniarProducto) as TextView
                TituloReseniarProducto.text=response.getString("nombrePlatilloBebida")
            }, Response.ErrorListener { error ->
                Toast.makeText(this, "No se encontró informacion", Toast.LENGTH_LONG).show()
            }
        )
        queue.add(jsonObjectRequest)
    }
    fun btnGuardarResenia(view:View){
        var queue= Volley.newRequestQueue(this)
        val ID_RESENIA = idResenia
        var url="https://promofood.000webhostapp.com/PromoFoodMYSQL/completarResenia.php"
        var calificacion=textCalificacion?.text.toString()
        var descripcion=textDescripcion?.text.toString()
        if(calificacion=="" || descripcion==""){
            Toast.makeText(this, "Llena todos los campos para hacer la reseña", Toast.LENGTH_LONG).show()
        }else{
            var resultadoPost= object : StringRequest(Request.Method.POST, url,
                Response.Listener<String>{ response->
                    Toast.makeText(this, "Reseña guardada", Toast.LENGTH_LONG).show()
                    setResult(Activity.RESULT_OK)
                    finish()
                }, Response.ErrorListener { error ->
                    Toast.makeText(this, "Error $error", Toast.LENGTH_LONG).show()
                }
            ){
                override fun getParams(): MutableMap<String, String>? {
                    val parametros=HashMap<String, String>()
                    parametros.put("id", ID_RESENIA)
                    parametros.put("puntuacion", calificacion)
                    parametros.put("descripcion", descripcion)
                    return parametros
                }
            }
            queue.add(resultadoPost)
        }
    }
}