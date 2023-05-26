package com.example.promofood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TableLayout
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException

class SesionClienteIniciadaActivity : AppCompatActivity() {
    var tbRestaurantes: TableLayout?=null
    var textBuscarRestaurante: EditText?=null
    var idCliente=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sesion_cliente_iniciada)
        textBuscarRestaurante=findViewById(R.id.textBuscarRestaurante)
        tbRestaurantes=findViewById(R.id.tbRestaurantes)
        idCliente=intent.getStringExtra("idCliente").toString()
        llenarTabla()
    }
    fun llenarTabla(){
        tbRestaurantes?.removeAllViews()
        var queue=Volley.newRequestQueue(this)
        var restauranteBuscar=textBuscarRestaurante?.text.toString()
        var url=""
        if (!restauranteBuscar.isEmpty()){
            url="https://promofood.000webhostapp.com/PromoFoodMYSQL/mostrarRestaurante.php?nombre=$restauranteBuscar"
        }else{
            url="https://promofood.000webhostapp.com/PromoFoodMYSQL/mostrarRestaurantes.php"
        }
        var jsonObjectRequest=JsonObjectRequest(Request.Method.GET, url, null,
        Response.Listener { response ->
            try{
                var jsonArray=response.getJSONArray("data")
                for(i in 0 until jsonArray.length()){
                    var jsonObject=jsonArray.getJSONObject(i)
                    val registro=LayoutInflater.from(this).inflate(R.layout.table_row_restaurantes,
                        null, false)
                    val colNombreRestaurante=registro.findViewById<View>(R.id.colNombreRestaurante) as TextView
                    val colEntrar=registro.findViewById<View>(R.id.colEntrar)
                    colNombreRestaurante.text=jsonObject.getString("nombre")
                    colEntrar.id=jsonObject.getString("id").toInt()
                    tbRestaurantes?.addView(registro)
                }
            }catch (e: JSONException){
                e.printStackTrace()
            }
        }, Response.ErrorListener { error ->
                Toast.makeText(this, "No se encontró ningún restaurante", Toast.LENGTH_LONG).show()
            }
            )
        queue.add(jsonObjectRequest)
    }
    fun clickTbEntrar(view:View){
        var intent= Intent(this, DetallesRestauranteActivity::class.java)
        intent.putExtra("id", view.id.toString())
        intent.putExtra("idCliente", idCliente)
        startActivity(intent)
    }
    fun clickBuscarRestaurante(view: View){
        llenarTabla()
    }
    fun clickBtnVerCarrito(view:View){
        var intent= Intent(this, DetallesCarritoActivity::class.java)
        intent.putExtra("idCliente", idCliente)
        startActivity(intent)
    }
}