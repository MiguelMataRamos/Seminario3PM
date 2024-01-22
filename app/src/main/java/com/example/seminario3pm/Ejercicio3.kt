package com.example.seminario3pm

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.graphics.drawable.toBitmap
import com.example.seminario3pm.databinding.ActivityEjercicio3Binding
import java.util.concurrent.atomic.AtomicInteger

class Ejercicio3 : AppCompatActivity() {
    private lateinit var binding: ActivityEjercicio3Binding
    var titulo: String? = null
    var texto: String? = null
    var icono: Drawable? = null
    var foto: Drawable? = null


    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = name
            val descriptionText = texto
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channel_id, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

        }
    }

    private fun mostrarNotificacion() {
        var bitmap = foto?.toBitmap()
        var builder = NotificationCompat.Builder(this, channel_id)
            .setSmallIcon(icono.hashCode())
            .setContentTitle(titulo)
            .setContentText(texto)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))


        with(NotificationManagerCompat.from(this)) {
            if (ActivityCompat.checkSelfPermission(
                    this@Ejercicio3,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {

            }
            notify(createNotificationId(), builder.build())
        }
    }

    companion object {
        const val name = "Notificacion"
        const val descripcion = "Descripcion del canal"
        const val app_id = "com.example.seminario3pm"
        const val channel_id = "${app_id}_c1"
        var id = AtomicInteger(0)

        fun createNotificationId(): Int {
            return id.incrementAndGet()
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityEjercicio3Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        createNotificationChannel()

        ArrayAdapter.createFromResource(
            this,
            R.array.iconos,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.iconos.adapter = adapter

        }
        binding.iconos.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                when(p2){
                    0 -> icono = null
                    1 -> icono = getDrawable(R.drawable.a)!!
                    2 -> icono = getDrawable(R.drawable.ninio)!!
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                icono = null!!
            }
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.fotos,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.fotos.adapter = adapter

        }
        binding.fotos.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long){
                when(p2){
                    0 -> foto = null
                    1 -> foto = getDrawable(R.drawable.paquirr_n)
                    2 -> foto = getDrawable(R.drawable.paquirr_n)
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                foto = null
            }
        }



        binding.btnNotificacion.setOnClickListener {
            if (validar()) {
                titulo = binding.txtInputTitulo.text.toString()
                texto = binding.txtInputTexto.text.toString()
                mostrarNotificacion()

            }

        }
    }

    fun validar(): Boolean {
        var fot = true
        var ic = true
        var titulo = true
        var texto = true

        if (binding.txtInputTitulo.text.toString().isEmpty()) {
            binding.txtInputTitulo.error = "Ingrese un titulo"
            titulo = false
        }
        if (binding.txtInputTexto.text.toString().isEmpty()) {
            binding.txtInputTexto.error = "Ingrese una descripcion"
            texto = false
        }
        if (icono == null){
            ic = false
            Toast.makeText(this, "Debes elegir un icono", Toast.LENGTH_SHORT).show()
        }
        if (foto == null){
            fot = false
            Toast.makeText(this, "Debes elegir una foto", Toast.LENGTH_SHORT).show()
        }

        return titulo && texto && ic && fot
    }
}