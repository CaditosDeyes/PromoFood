package com.example.promofood

import android.app.Activity
import android.content.Intent
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

class SesionRestauranteIniciadaActivity : AppCompatActivity() {
    var tbPlatillos: TableLayout?=null
    var tbBebidas: TableLayout?=null
    var menuPerteneciente=""
    var pedidoEnCamino=false

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sesion_restaurante_iniciada)
        menuPerteneciente = intent.getStringExtra("id").toString()
        tbPlatillos=findViewById(R.id.tbPlatillos)
        tbBebidas=findViewById(R.id.tbBebidas)
        obtenerHoraEntrega()
        llenarTablaPlatillos()
        llenarTablaBebidas()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            llenarTablaPlatillos()
            llenarTablaBebidas()
        }
    }

    fun llenarTablaPlatillos(){
        tbPlatillos?.removeAllViews()
        var queue= Volley.newRequestQueue(this)
        var url=""
        val MENU_PERTENECIENTE=menuPerteneciente
        url="https://promofood.000webhostapp.com/PromoFoodMYSQL/mostrarPlatillos.php?idMenuPerteneciente=$MENU_PERTENECIENTE"
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
        intent= Intent(this, DetallesPlatilloRestaurante::class.java)
        intent.putExtra("id", view.id.toString())
        startActivity(intent)

    }
    fun clickBtnVerDetallesBebida(view:View){
        var intent= Intent(this, DetallesBebidaRestaurante::class.java)
        intent.putExtra("id", view.id.toString())
        startActivity(intent)
    }

    fun clickBtnEditarMenu(view: View){
        var intent= Intent(this,InsertarPlatilloBebidaActivity::class.java)
        val requestCode = 1
        intent.putExtra("idMenu", menuPerteneciente)
        startActivityForResult(intent, requestCode)
    }

    fun clickBtnVerPedidos(view: View){
        var intent= Intent(this, VerPedidosRestauranteActivity::class.java)
        intent.putExtra("idPedido", menuPerteneciente)
        startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun comprobarHora(idPedido:String, horaEntregaString:String){
        val actual: LocalTime = LocalTime.now()
        var horaActualString=""
        var horas=actual.getHour()
        var minutos=actual.getMinute()
        if (minutos<=9){
            if(horas<=9){
                horaActualString="0"+horas.toString()+":0"+minutos
            }else{
                horaActualString=horas.toString()+":0"+minutos
            }
        }else{
            if(horas<=9){
                horaActualString="0"+horas.toString()+":"+minutos
            }else{
                horaActualString=horas.toString()+":"+minutos
            }
        }
        if(horaEntregaString=="No hay pedido"){

        }else{
            var horaActual = LocalTime.parse(horaActualString);
            var horaEntrega = LocalTime.parse(horaEntregaString);
            val minutosDiferencia = ChronoUnit.MINUTES.between(horaActual, horaEntrega).toInt() //Se comparan las horas y retorna la diferencia de minutos
            if (minutosDiferencia<0){
                pedidoEnCamino=false
                cambiarEstadoPedido(idPedido)
            }else{
                pedidoEnCamino=true
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun obtenerHoraEntrega(){
        val queue= Volley.newRequestQueue(this)
        val RESTAURANTE_PERTENECIENTE=menuPerteneciente
        var url="https://promofood.000webhostapp.com/PromoFoodMYSQL/mostrarPedidosRestaurante.php?idRestaurantePerteneciente=$RESTAURANTE_PERTENECIENTE"
        var jsonObjectRequest= JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                try{
                    var jsonArray=response.getJSONArray("data")
                    var horaEntregaString=""
                    var idPedido=""
                    for(i in 0 until jsonArray.length()){
                        var jsonObject=jsonArray.getJSONObject(i)
                        horaEntregaString=jsonObject.getString("horaEntrega")
                        idPedido=jsonObject.getString("id")
                        comprobarHora(idPedido, horaEntregaString)
                    }
                }catch (e: JSONException){
                    e.printStackTrace()
                }
            }, Response.ErrorListener { error ->
            }
        )
        queue.add(jsonObjectRequest)
    }

    fun cambiarEstadoPedido(idPedido:String){
        var queue= Volley.newRequestQueue(this)
        val ID=idPedido
        val RESTAURANTE_PERTENECIENTE=menuPerteneciente
        var url="https://promofood.000webhostapp.com/PromoFoodMYSQL/actualizarPedidoRestaurante.php?idRestaurantePerteneciente=$RESTAURANTE_PERTENECIENTE&id=$ID"
        var resultadoPost= object : StringRequest(Request.Method.POST, url,
            Response.Listener<String>{ response->
            }, Response.ErrorListener { error ->
                Toast.makeText(this, "Error $error", Toast.LENGTH_LONG).show()
            }
        ){
            override fun getParams(): MutableMap<String, String>? {
                val parametros=HashMap<String, String>()
                parametros.put("idRestaurantePerteneciente", RESTAURANTE_PERTENECIENTE)
                return parametros
            }
        }
        queue.add(resultadoPost)
    }
}