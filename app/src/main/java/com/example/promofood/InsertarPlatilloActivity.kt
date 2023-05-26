package com.example.promofood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class InsertarPlatilloActivity : AppCompatActivity() {
    var txtNombrePlatillo: EditText? = null
    var txtPrecioPlatillo: EditText? = null
    var txtIngredientesPlatillo: EditText? = null
    var idMenu=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertar_platillo)
        txtNombrePlatillo = findViewById(R.id.txtNombrePlatillo)
        txtPrecioPlatillo = findViewById(R.id.txtPrecioPlatillo)
        txtIngredientesPlatillo = findViewById(R.id.txtIngredientesPlatillo)
        val ID_MENU=intent.getStringExtra("idMenu").toString()
        idMenu=ID_MENU
    }
    fun clickBtnAnadirPlatillo(view: View){
        val ID_MENU = idMenu
        val url="https://promofood.000webhostapp.com/PromoFoodMYSQL/insertarPlatillo.php"
        val queue= Volley.newRequestQueue(this)
        var nombre=txtNombrePlatillo?.text.toString()
        var precio=txtPrecioPlatillo?.text.toString()
        var ingredientes=txtIngredientesPlatillo?.text.toString()
        if(nombre=="" || precio=="" || ingredientes==""){
            Toast.makeText(this, "Llene todo los campos para registrar el platillo", Toast.LENGTH_LONG).show()
        }else{
            var resultadoPost = object : StringRequest(Request.Method.POST,url,
                Response.Listener<String> { response ->
                    Toast.makeText(this,"Platillo insertado exitosamente", Toast.LENGTH_LONG).show()
                    finish()
                }, Response.ErrorListener { error ->
                    Toast.makeText(this,"Error $error", Toast.LENGTH_LONG).show()
                }){
                override fun getParams(): MutableMap<String, String>? {
                    val parametros=HashMap<String,String>()
                    parametros.put("idMenuPerteneciente", ID_MENU)
                    parametros.put("nombre", nombre)
                    parametros.put("precio", precio)
                    parametros.put("ingredientes", ingredientes)
                    return parametros
                }
            }
            queue.add(resultadoPost)
            txtNombrePlatillo?.setText("")
            txtPrecioPlatillo?.setText("")
            txtIngredientesPlatillo?.setText("")
        }
    }
    fun clickBtnCancelar1(view: View){
        finish()
    }
}