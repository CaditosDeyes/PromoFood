package com.example.promofood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TableLayout
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException

class DetallesRestauranteActivity : AppCompatActivity() {
    var idMenu=""
    var idCliente=""
    var idRestaurante=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_restaurante)
        idCliente=intent.getStringExtra("idCliente").toString()
        idRestaurante= intent.getStringExtra("id").toString()
        llenarTitulos()
    }
    fun llenarTitulos(){
        var queue= Volley.newRequestQueue(this)
        val ID_RESTAURANTE = idRestaurante
        val url="https://promofood.000webhostapp.com/PromoFoodMYSQL/mostrarRestauranteID.php?id=$ID_RESTAURANTE"
        var jsonObjectRequest= JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                val textNombreRestaurante=findViewById<View>(R.id.textNombreRestaurante) as TextView
                val textDomicilioBD=findViewById<View>(R.id.textDomicilioBD) as TextView
                val textTelefonoBD=findViewById<View>(R.id.textTelefonoBD) as TextView
                textNombreRestaurante.text=response.getString("nombre")
                textDomicilioBD.text=response.getString("domicilio")
                textTelefonoBD.text=response.getString("numeroTelefono")
                idMenu=response.getString("id")
            }, Response.ErrorListener { error ->
                Toast.makeText(this, "No se encontró el restaurante", Toast.LENGTH_LONG).show()
            }
        )
        queue.add(jsonObjectRequest)
    }
    fun clickBtnVerMenu(view: View){
        var intent= Intent(this, VerMenuPorClienteActivity::class.java)
        intent.putExtra("id", idMenu)
        intent.putExtra("idCliente", idCliente)
        intent.putExtra("idRestaurante", idRestaurante)
        startActivity(intent)
    }
    fun clickBtnVerCarrito(view:View){
        var intent= Intent(this, DetallesCarritoActivity::class.java)
        intent.putExtra("idCliente", idCliente)
        intent.putExtra("idRestaurante", idRestaurante)
        startActivity(intent)
    }
}