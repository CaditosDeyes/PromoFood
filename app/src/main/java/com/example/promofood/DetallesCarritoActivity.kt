package com.example.promofood

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TableLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import kotlinx.coroutines.*
import java.text.DecimalFormat
import java.util.*
import kotlin.collections.HashMap

class DetallesCarritoActivity : AppCompatActivity() {
    var tbCarrito: TableLayout?=null
    var textDireccionEntrega: EditText?=null
    var idCliente=""
    var precioFinal=0.0
    var horaEntregaString=""
    var pedidoEnCamino=false
    var listaIdRestaurantesSinRepeticiones:MutableList<Int> = mutableListOf()
    var listaResumenes:MutableList<String> = mutableListOf()
    var listaPrecios:MutableList<Double> = mutableListOf()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_carrito)
        textDireccionEntrega=findViewById(R.id.textDireccionEntrega)
        idCliente=intent.getStringExtra("idCliente").toString()
        tbCarrito=findViewById(R.id.tbCarrito)
        llenarTabla()
        obtenerHoraEntrega()
    }
    fun llenarTabla(){
        val textPrecioFinal=findViewById(R.id.textPrecioFinal) as TextView
        val df = DecimalFormat("#.##")
        var queue= Volley.newRequestQueue(this)
        val ID_CLIENTE=idCliente
        var url="https://promofood.000webhostapp.com/PromoFoodMYSQL/mostrarCarrito.php?idCliente=$ID_CLIENTE&solicitado=0"
        var listaIdRestaurantes:MutableList<Int> = mutableListOf()
        tbCarrito?.removeAllViews()
        precioFinal=0.0
        textPrecioFinal.text="0"
        var jsonObjectRequest= JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                try{
                    var jsonArray=response.getJSONArray("data")
                    for(i in 0 until jsonArray.length()){
                        var jsonObject=jsonArray.getJSONObject(i)
                        val registro= LayoutInflater.from(this).inflate(R.layout.table_row_carrito,
                            null, false)
                        val colResumen=registro.findViewById<View>(R.id.colResumen) as TextView
                        colResumen.text=jsonObject.getString("resumen")
                        precioFinal=precioFinal+jsonObject.getString("precioIndividual").toDouble()
                        tbCarrito?.addView(registro)
                        listaIdRestaurantes.add(jsonObject.getString("idRestaurantePerteneciente").toInt())
                    }
                    listaIdRestaurantesSinRepeticiones=listaIdRestaurantes.distinct()?.toList() as MutableList<Int>
                    precioFinal= df.format(precioFinal).toDouble()
                    textPrecioFinal.text=precioFinal.toString()
                    generarResumenPorPedido()
                }catch (e: JSONException){
                    e.printStackTrace()
                }
            }, Response.ErrorListener { error ->

            }
        )
        queue.add(jsonObjectRequest)
    }
    fun generarResumenPorPedido(){
        val queue= Volley.newRequestQueue(this)
        val ID_CLIENTE=idCliente
        for(i in 0 until listaIdRestaurantesSinRepeticiones.size){
            val idRestaurante=listaIdRestaurantesSinRepeticiones.get(i)
            var url="https://promofood.000webhostapp.com/PromoFoodMYSQL/mostrarCarritoPorRestaurante.php?idCliente=$ID_CLIENTE&solicitado=0&idRestaurantePerteneciente=$idRestaurante"
            var resumen=""
            var precio=0.0
            val jsonObjectRequest= JsonObjectRequest(
                Request.Method.GET, url, null,
                Response.Listener { response ->
                    var jsonArray=response.getJSONArray("data")
                    for(i in 0 until jsonArray.length()){
                        var jsonObject=jsonArray.getJSONObject(i)
                        resumen=resumen+jsonObject.getString("resumen")+"\n"
                        precio=precio+jsonObject.getString("precioIndividual").toDouble()
                    }
                    listaResumenes.add(resumen)
                    listaPrecios.add(precio)
                }, Response.ErrorListener { error ->
                }
            )
            queue.add(jsonObjectRequest)
        }
    }

    fun clickBtnVerEstadoPedido(view:View){
        if(pedidoEnCamino){
            var intent= Intent(this, VerPedidosClienteActivity::class.java)
            intent.putExtra("idCliente", idCliente)
            startActivity(intent)
        }else{
            Toast.makeText(this, "No hay ningún pedido en camino", Toast.LENGTH_LONG).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun clickBtnRealizarPedido(view:View){
        if(!pedidoEnCamino){
            val direccionEntrega = textDireccionEntrega?.text.toString()
            if (direccionEntrega != ""){
                for(i in 0 until listaIdRestaurantesSinRepeticiones.size){
                    realizarPedido(listaIdRestaurantesSinRepeticiones.get(i), listaResumenes.get(i), listaPrecios.get(i))
                }
            }else{
                Toast.makeText(this, "Ingrese una dirección valida", Toast.LENGTH_LONG).show()
            }
        }
        else{
            Toast.makeText(this, "Hay un pedido en camino", Toast.LENGTH_LONG).show()
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun realizarPedido(idRestaurante:Int, resumen:String, precio:Double){
        var queue= Volley.newRequestQueue(this)
        val horaActual: LocalTime = LocalTime.now()
        var tiempoPorRestaurantes=5*listaIdRestaurantesSinRepeticiones.size
        var tiempoExtra=valorRandom(25+tiempoPorRestaurantes..36+tiempoPorRestaurantes)
        var horas=horaActual.getHour()
        var minutos=horaActual.getMinute()+tiempoExtra
        var horaEntrega=""
        if(minutos>=60){
            horas++
            minutos=minutos-60
        }
        if (minutos<=9){
            if(horas==24){
                horaEntrega="00:0"+minutos
            }else{
                if(horas<=9){
                    horaEntrega="0"+horas.toString()+":0"+minutos
                }else{
                    horaEntrega=horas.toString()+":0"+minutos
                }
            }
        }else{
            if(horas==24){
                horaEntrega="00:"+minutos
            }else{
                if(horas<=9){
                    horaEntrega="0"+horas.toString()+":"+minutos
                }else{
                    horaEntrega=horas.toString()+":"+minutos
                }
            }
        }
        val direccionEntrega = textDireccionEntrega?.text.toString()
        val ID_CLIENTE=idCliente
        var url="https://promofood.000webhostapp.com/PromoFoodMYSQL/insertarPedido.php"
        if(precioFinal>0.0){
            var resultadoPost= object : StringRequest(Request.Method.POST, url,
                Response.Listener<String>{ response->
                    Toast.makeText(this, "Pedido realizado", Toast.LENGTH_LONG).show()
                    pedidoEnCamino=true
                    actualizarCarrito()
                }, Response.ErrorListener { error ->
                    Toast.makeText(this, "Error $error", Toast.LENGTH_LONG).show()
                }
            ){
                override fun getParams(): MutableMap<String, String>? {
                    val parametros=HashMap<String, String>()
                    parametros.put("idClienteSolicitado", ID_CLIENTE)
                    parametros.put("horaEntrega", horaEntrega)
                    parametros.put("estatus", "No entregado")
                    parametros.put("direccionEntrega", direccionEntrega)
                    parametros.put("precioFinal", precio.toString())
                    parametros.put("idRestaurantePerteneciente", idRestaurante.toString())
                    parametros.put("resumen",resumen)
                    return parametros
                }
            }
            queue.add(resultadoPost)
            textDireccionEntrega?.setText("")
        }else{
            Toast.makeText(this, "No hay productos en el carrito", Toast.LENGTH_LONG).show()
        }
    }
    fun actualizarCarrito(){
        var queue= Volley.newRequestQueue(this)
        val ID_CLIENTE=idCliente
        var url="https://promofood.000webhostapp.com/PromoFoodMYSQL/actualizarCarrito.php?idCliente=$ID_CLIENTE"
        var resultadoPost= object : StringRequest(Request.Method.POST, url,
            Response.Listener<String>{ response->
                llenarTabla()
            }, Response.ErrorListener { error ->
                Toast.makeText(this, "Error $error", Toast.LENGTH_LONG).show()
            }
        ){
            override fun getParams(): MutableMap<String, String>? {
                val parametros=HashMap<String, String>()
                parametros.put("idCliente", idCliente)
                return parametros
            }
        }
        queue.add(resultadoPost)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun comprobarHora(){
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
                cambiarEstadoPedido()
                cambiarEstadoResenia()
            }else{
                pedidoEnCamino=true
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun obtenerHoraEntrega(){
        val queue= Volley.newRequestQueue(this)
        val ID_CLIENTE=idCliente
        var url="https://promofood.000webhostapp.com/PromoFoodMYSQL/mostrarPedido.php?idClienteSolicitado=$ID_CLIENTE"
        val jsonObjectRequest= JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                horaEntregaString=response.getString("horaEntrega")
                comprobarHora()
            }, Response.ErrorListener { error ->
                horaEntregaString="No hay pedido"
                comprobarHora()
            }
        )
        queue.add(jsonObjectRequest)
    }

    fun cambiarEstadoPedido(){
        var queue= Volley.newRequestQueue(this)
        val ID_CLIENTE=idCliente
        var url="https://promofood.000webhostapp.com/PromoFoodMYSQL/actualizarPedido.php?idClienteSolicitado=$ID_CLIENTE"
        var resultadoPost= object : StringRequest(Request.Method.POST, url,
            Response.Listener<String>{ response->
            }, Response.ErrorListener { error ->
                Toast.makeText(this, "Error $error", Toast.LENGTH_LONG).show()
            }
        ){
            override fun getParams(): MutableMap<String, String>? {
                val parametros=HashMap<String, String>()
                parametros.put("idClienteSolicitado", idCliente)
                return parametros
            }
        }
        queue.add(resultadoPost)
    }

    fun cambiarEstadoResenia(){
        var queue= Volley.newRequestQueue(this)
        val ID_CLIENTE=idCliente
        var url="https://promofood.000webhostapp.com/PromoFoodMYSQL/actualizarResenia.php?idClienteSolicitado=$ID_CLIENTE"
        var resultadoPost= object : StringRequest(Request.Method.POST, url,
            Response.Listener<String>{ response->
                var intent= Intent(this, VerReseniasDisponiblesActivity::class.java)
                intent.putExtra("idCliente", idCliente)
                startActivity(intent)
            }, Response.ErrorListener { error ->
                Toast.makeText(this, "Error $error", Toast.LENGTH_LONG).show()
            }
        ){
            override fun getParams(): MutableMap<String, String>? {
                val parametros=HashMap<String, String>()
                parametros.put("idClienteSolicitado", idCliente)
                return parametros
            }
        }
        queue.add(resultadoPost)
    }

    fun valorRandom(valores: IntRange) : Int {
        var r = Random()
        var valorRandom = r.nextInt(valores.last - valores.first) + valores.first
        return valorRandom
    }
}