package com.example.promofood

import android.app.Activity
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

class VerReseniasDisponiblesActivity : AppCompatActivity() {
    var idCliente=""
    var tbReseniasDisponibles: TableLayout?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_resenias_disponibles)
        idCliente=intent.getStringExtra("idCliente").toString()
        tbReseniasDisponibles=findViewById(R.id.tbReseniasDisponibles)
        llenarTabla()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            llenarTabla()
        }
    }

    fun llenarTabla(){
        tbReseniasDisponibles?.removeAllViews()
        var queue= Volley.newRequestQueue(this)
        val ID_CLIENTE=idCliente
        val url="https://promofood.000webhostapp.com/PromoFoodMYSQL/mostrarResenia.php?idClienteSolicitado=$ID_CLIENTE"
        var jsonObjectRequest= JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                try{
                    var jsonArray=response.getJSONArray("data")
                    for(i in 0 until jsonArray.length()){
                        var jsonObject=jsonArray.getJSONObject(i)
                        val registro= LayoutInflater.from(this).inflate(R.layout.table_row_ver_resenias_disponibles,
                            null, false)
                        val colPlatilloReseniar=registro.findViewById<View>(R.id.colPlatilloReseniar) as TextView
                        val colEntrarResenia=registro.findViewById<View>(R.id.colEntrarResenia)
                        colPlatilloReseniar.text=jsonObject.getString("nombrePlatilloBebida")
                        colEntrarResenia.id=jsonObject.getString("id").toInt()
                        tbReseniasDisponibles?.addView(registro)
                    }
                }catch (e: JSONException){
                    e.printStackTrace()
                }
            }, Response.ErrorListener { error ->
                Toast.makeText(this, "Todas las reseñas calificadas", Toast.LENGTH_SHORT).show()
                finish()
            }
        )
        queue.add(jsonObjectRequest)
    }

    fun btnClickOmitirResenias(view: View){
        val queue= Volley.newRequestQueue(this)
        val ID_CLIENTE=idCliente
        val url="https://promofood.000webhostapp.com/PromoFoodMYSQL/borrarResenias.php?idClienteSolicitado=$ID_CLIENTE"
        val jsonObjectRequest= JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
            }, Response.ErrorListener { error ->
            }
        )
        queue.add(jsonObjectRequest)
        finish()
    }
    fun clickTbEntrar(view:View){
        var intent= Intent(this, ReseniarProductoActivity::class.java)
        val requestCode = 1
        intent.putExtra("idResenia", view.id.toString())
        intent.putExtra("idCliente", idCliente)
        startActivityForResult(intent, requestCode)
    }
}