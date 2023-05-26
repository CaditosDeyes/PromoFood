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

class InsertarBebidaActivity : AppCompatActivity() {
    var txtNombreBebida: EditText? = null
    var txtPrecioBebida: EditText? = null
    var txtIngredientesBebida: EditText? = null
    var idMenu=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertar_bebida)
        txtNombreBebida = findViewById(R.id.txtNombreBebida)
        txtPrecioBebida = findViewById(R.id.txtPrecioBebida)
        txtIngredientesBebida = findViewById(R.id.txtIngredientesBebida)
        val ID_MENU=intent.getStringExtra("idMenu").toString()
        idMenu=ID_MENU
    }

    fun clickBtnAnadirBebida(view: View){
        val ID_MENU = idMenu
        val url="https://promofood.000webhostapp.com/PromoFoodMYSQL/insertarBebida.php"
        val queue= Volley.newRequestQueue(this)
        var nombre=txtNombreBebida?.text.toString()
        var precio=txtPrecioBebida?.text.toString()
        var ingredientes=txtIngredientesBebida?.text.toString()
        if(nombre=="" || precio=="" || ingredientes==""){
            Toast.makeText(this, "Llene todo los campos para registrar la bebida", Toast.LENGTH_LONG).show()
        }else{
            var resultadoPost = object : StringRequest(Request.Method.POST,url,
                Response.Listener<String> { response ->
                    Toast.makeText(this,"Bebida insertada exitosamente", Toast.LENGTH_LONG).show()
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
            txtNombreBebida?.setText("")
            txtPrecioBebida?.setText("")
            txtIngredientesBebida?.setText("")
        }
    }

    fun clickBtnCancelar2(view: View){
        finish()
    }
}