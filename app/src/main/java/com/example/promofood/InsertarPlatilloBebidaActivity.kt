package com.example.promofood

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.content.Intent
import android.widget.Toast

class InsertarPlatilloBebidaActivity : AppCompatActivity() {
    var idMenu=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertar_platillo_bebida)
        idMenu=intent.getStringExtra("idMenu").toString()
    }

    fun clickBtnAgregarPlatillo(view: View){
        var intent= Intent(this, InsertarPlatilloActivity::class.java)
        intent.putExtra("idMenu", idMenu)
        startActivity(intent)
    }

    fun clickBtnAgregarBebida(view: View){
        var intent= Intent(this, InsertarBebidaActivity::class.java)
        intent.putExtra("idMenu", idMenu)
        startActivity(intent)
    }
    fun clickBtnRegresar(view: View){
        setResult(Activity.RESULT_OK)
        finish()
    }
}