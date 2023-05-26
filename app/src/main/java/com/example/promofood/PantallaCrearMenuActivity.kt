package com.example.promofood

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class PantallaCrearMenuActivity : AppCompatActivity() {
    var txtNombreMenu:EditText?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_crear_menu)
        txtNombreMenu=findViewById(R.id.txtNombreMenu)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            finish()
        }
    }

    fun btnCrearMenu(view:View){
        val url="https://promofood.000webhostapp.com/PromoFoodMYSQL/insertarMenu.php"
        val queue=Volley.newRequestQueue(this)
        val nombre=txtNombreMenu?.text.toString()
        val idRestaurante=intent.getStringExtra("idRestaurante").toString()
        if(nombre==""){
            Toast.makeText(this, "Ingrese un nombre valido", Toast.LENGTH_LONG).show()
        }else{
            val resultadoPost = object : StringRequest(Request.Method.POST,url,
                Response.Listener<String> { response ->
                    Toast.makeText(this,"Menu insertado exitosamente",Toast.LENGTH_LONG).show()
                    var intent= Intent(this, InsertarPlatilloBebidaActivity::class.java)
                    val requestCode = 1
                    intent.putExtra("idMenu", idRestaurante)
                    //startActivity(intent)
                    startActivityForResult(intent, requestCode)
                },Response.ErrorListener { error ->
                    Toast.makeText(this,"Error $error",Toast.LENGTH_LONG).show()
                }){
                override fun getParams(): MutableMap<String, String>? {
                    val parametros=HashMap<String,String>()
                    parametros.put("nombre", nombre)
                    return parametros
                }
            }
            queue.add(resultadoPost)
            txtNombreMenu?.setText("")
        }
    }
    fun clickBtnCancelar(view: View){
        finish()
    }
}