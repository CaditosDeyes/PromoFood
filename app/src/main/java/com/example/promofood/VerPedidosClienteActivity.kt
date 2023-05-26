package com.example.promofood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import java.text.DecimalFormat

class VerPedidosClienteActivity : AppCompatActivity() {
    var idCliente=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_pedidos_cliente)
        idCliente=intent.getStringExtra("idCliente").toString()
        llenarTitulos()
    }
    fun llenarTitulos(){
        var queue= Volley.newRequestQueue(this)
        val df = DecimalFormat("#.##")
        val ID_CLIENTE=idCliente
        var resumen=""
        var precioFinal=0.0
        val url="https://promofood.000webhostapp.com/PromoFoodMYSQL/mostrarPedidos.php?idClienteSolicitado=$ID_CLIENTE"
        var jsonObjectRequest= JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                try{
                    var jsonArray=response.getJSONArray("data")
                    val textValorEstatus=findViewById<View>(R.id.textValorEstatus) as TextView
                    val textValorHora=findViewById<View>(R.id.textValorHora) as TextView
                    val textValorPrecioFinal=findViewById<View>(R.id.textValorPrecioFinal) as TextView
                    val textValorResumen=findViewById<View>(R.id.textValorResumen) as TextView
                    var status=""
                    var hora=""
                    for(i in 0 until jsonArray.length()){
                        var jsonObject=jsonArray.getJSONObject(i)
                        status=jsonObject.getString("estatus")
                        hora=jsonObject.getString("horaEntrega")
                        precioFinal=precioFinal+jsonObject.getString("precioFinal").toDouble()
                        resumen=resumen+jsonObject.getString("resumen")+"\n"
                    }
                    textValorEstatus.text=status
                    textValorHora.text=hora
                    precioFinal= df.format(precioFinal).toDouble()
                    textValorPrecioFinal.text=precioFinal.toString()
                    textValorResumen.text=resumen
                }catch (e: JSONException){
                    e.printStackTrace()
                }
            }, Response.ErrorListener { error ->
                Toast.makeText(this, "No se encontró ningún pedido", Toast.LENGTH_LONG).show()
            }
        )
        queue.add(jsonObjectRequest)
    }
}