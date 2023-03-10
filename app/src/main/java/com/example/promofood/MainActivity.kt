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

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun clickbtnRegistrarCliente(view:View){
        var intent= Intent(this, RegistroClienteActivity::class.java)
        startActivity(intent)
    }

    fun clickbtnRegistrarRestaurante(view:View){
        var intent= Intent(this, RegistroRestauranteActivity::class.java)
        startActivity(intent)
    }
}