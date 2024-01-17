package com.example.seminario3pm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.seminario3pm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var bind: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.btnEjercicio1.setOnClickListener {
            var intent = Intent(this, Ejercicio1::class.java)
            startActivity(intent)
        }

        bind.btnEjercicio2.setOnClickListener {
            var intent = Intent(this, Ejercicio2::class.java)
            startActivity(intent)
        }

        bind.btnEjercicio3.setOnClickListener {
            var intent = Intent(this, Ejercicio3::class.java)
            startActivity(intent)
        }

    }
}