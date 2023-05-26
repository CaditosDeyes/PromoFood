package com.example.promofood

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

class VerReseniasBebidaActivity : AppCompatActivity() {
    var idBebida=""
    var tbProductos: TableLayout?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_resenias_bebida)
        idBebida=intent.getStringExtra("idBebida").toString()
        tbProductos=findViewById(R.id.tbProductos)
        llenarTabla()
    }
    fun llenarTabla(){
        val ID_BEBIDA=idBebida
        tbProductos?.removeAllViews()
        var queue= Volley.newRequestQueue(this)
        var url="https://promofood.000webhostapp.com/PromoFoodMYSQL/mostrarReseniasBebida.php?idBebida=$ID_BEBIDA"
        var jsonObjectRequest= JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                try{
                    var jsonArray=response.getJSONArray("data")
                    for(i in 0 until jsonArray.length()){
                        var jsonObject=jsonArray.getJSONObject(i)
                        val registro= LayoutInflater.from(this).inflate(R.layout.table_row_resenias_productos,
                            null, false)
                        val colDescripcionProducto=registro.findViewById<View>(R.id.colDescripcionProducto) as TextView
                        val colCalificacionProducto=registro.findViewById<View>(R.id.colCalificacionProducto) as TextView
                        colDescripcionProducto.text=jsonObject.getString("descripcion")
                        colCalificacionProducto.text=jsonObject.getString("puntuacion")
                        tbProductos?.addView(registro)
                    }
                }catch (e: JSONException){
                    e.printStackTrace()
                }
            }, Response.ErrorListener { error ->
                Toast.makeText(this, "Aun no hay ninguna reseña", Toast.LENGTH_LONG).show()
            }
        )
        queue.add(jsonObjectRequest)
    }
    fun clickBtnRegresar(view: View){
        finish()
    }
}