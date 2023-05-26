package com.example.promofood

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TableLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import java.time.LocalTime
import java.time.temporal.ChronoUnit

class VerPedidosRestauranteActivity : AppCompatActivity() {
    var tbPedidos:TableLayout?=null
    var restaurantePerteneciente=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_pedidos_restaurante)
        restaurantePerteneciente= intent.getStringExtra("idPedido").toString()
        tbPedidos=findViewById(R.id.tbPedidos)
        llenarTablaPedidos()
    }

    fun llenarTablaPedidos(){
        tbPedidos?.removeAllViews()
        var queue= Volley.newRequestQueue(this)
        val RESTAURANTE_PERTENECIENTE=restaurantePerteneciente
        val url="https://promofood.000webhostapp.com/PromoFoodMYSQL/mostrarPedidosRestaurante.php?idRestaurantePerteneciente=$RESTAURANTE_PERTENECIENTE"
        var jsonObjectRequest= JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                try{
                    var jsonArray=response.getJSONArray("data")
                    for(i in 0 until jsonArray.length()){
                        var jsonObject=jsonArray.getJSONObject(i)
                        val registro= LayoutInflater.from(this).inflate(R.layout.table_row_ver_pedidos_restaurante,
                            null, false)
                        val colEstatus=registro.findViewById<View>(R.id.colEstatus) as TextView
                        val colDireccionEntrega=registro.findViewById<View>(R.id.colDireccionEntrega) as TextView
                        val colResumenPedido=registro.findViewById<View>(R.id.colResumenPedido) as TextView
                        colEstatus.text=jsonObject.getString("estatus")
                        colDireccionEntrega.text=jsonObject.getString("direccionEntrega")
                        colResumenPedido.text=jsonObject.getString("resumen")
                        tbPedidos?.addView(registro)
                    }
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
