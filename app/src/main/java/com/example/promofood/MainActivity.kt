package com.example.promofood

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun clickBtnIniciarSesionCliente(view: View){
        var intent= Intent(this, LoginClienteActivity::class.java)
        startActivity(intent)
    }

    fun clickbtnRegistrarCliente(view:View){
        var intent= Intent(this, RegistroClienteActivity::class.java)
        startActivity(intent)
    }
    fun clickBtnIniciarSesionRestaurante(view: View){
        var intent= Intent(this, LoginRestauranteActivity::class.java)
        startActivity(intent)
    }
    fun clickbtnRegistrarRestaurante(view:View){
        var intent= Intent(this, RegistroRestauranteActivity::class.java)
        startActivity(intent)
    }
}


