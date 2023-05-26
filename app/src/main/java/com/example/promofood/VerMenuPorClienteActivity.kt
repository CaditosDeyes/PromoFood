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

class VerMenuPorClienteActivity : AppCompatActivity() {
    var tbPlatillos: TableLayout?=null
    var tbBebidas: TableLayout?=null
    var menuPerteneciente=""
    var idCliente=""
    var idRestaurante=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_menu_por_cliente)
        menuPerteneciente = intent.getStringExtra("id").toString()
        tbPlatillos=findViewById(R.id.tbPlatillos)
        tbBebidas=findViewById(R.id.tbBebidas)
        idCliente=intent.getStringExtra("idCliente").toString()
        idRestaurante= intent.getStringExtra("idRestaurante").toString()
        llenarTablaPlatillos()
        llenarTablaBebidas()
    }
    fun llenarTablaPlatillos(){
        tbPlatillos?.removeAllViews()
        var queue= Volley.newRequestQueue(this)
        val MENU_PERTENECIENTE=menuPerteneciente
        var url="https://promofood.000webhostapp.com/PromoFoodMYSQL/mostrarPlatillos.php?idMenuPerteneciente=$MENU_PERTENECIENTE"
        var jsonObjectRequest= JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                try{
                    var jsonArray=response.getJSONArray("data")
                    for(i in 0 until jsonArray.length()){
                        var jsonObject=jsonArray.getJSONObject(i)
                        val registro= LayoutInflater.from(this).inflate(R.layout.table_row_platillos,
                            null, false)
                        val colNombrePlatillo=registro.findViewById<View>(R.id.colNombrePlatillo) as TextView
                        val colVerDetalles=registro.findViewById<View>(R.id.colVerDetallesPlatillo)
                        colNombrePlatillo.text=jsonObject.getString("nombre")
                        colVerDetalles.id=jsonObject.getString("id").toInt()
                        tbPlatillos?.addView(registro)
                    }
                }catch (e: JSONException){
                    e.printStackTrace()
                }
            }, Response.ErrorListener { error ->
                Toast.makeText(this, "No se encontró ningún platillo", Toast.LENGTH_LONG).show()
            }
        )
        queue.add(jsonObjectRequest)
    }
    fun llenarTablaBebidas(){
        tbBebidas?.removeAllViews()
        var queue= Volley.newRequestQueue(this)
        val MENU_PERTENECIENTE=menuPerteneciente
        var url="https://promofood.000webhostapp.com/PromoFoodMYSQL/mostrarBebidas.php?idMenuPerteneciente=$MENU_PERTENECIENTE"
        var jsonObjectRequest= JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                try{
                    var jsonArray=response.getJSONArray("data")
                    for(i in 0 until jsonArray.length()){
                        var jsonObject=jsonArray.getJSONObject(i)
                        val registro= LayoutInflater.from(this).inflate(R.layout.table_row_bebidas,
                            null, false)
                        val colNombrePlatillo=registro.findViewById<View>(R.id.colNombreBebida) as TextView
                        val colVerDetalles=registro.findViewById<View>(R.id.colVerDetallesBebida)
                        colNombrePlatillo.text=jsonObject.getString("nombre")
                        colVerDetalles.id=jsonObject.getString("id").toInt()
                        tbBebidas?.addView(registro)
                    }
                }catch (e: JSONException){
                    e.printStackTrace()
                }
            }, Response.ErrorListener { error ->
                Toast.makeText(this, "No se encontró ningúna bebida", Toast.LENGTH_LONG).show()
            }
        )
        queue.add(jsonObjectRequest)
    }
    fun clickBtnVerDetallesPlatillo(view:View){
        intent= Intent(this, DetallesPlatilloActivity::class.java)
        intent.putExtra("id", view.id.toString())
        intent.putExtra("idCliente", idCliente)
        intent.putExtra("idRestaurante", idRestaurante)
        startActivity(intent)

    }
    fun clickBtnVerDetallesBebida(view:View){
        var intent= Intent(this, DetallesBebidaActivity::class.java)
        intent.putExtra("id", view.id.toString())
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