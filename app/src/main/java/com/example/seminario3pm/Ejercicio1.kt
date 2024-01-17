package com.example.seminario3pm

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.util.concurrent.atomic.AtomicInteger

class Ejercicio1 : AppCompatActivity() {
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = name
            val descriptionText = descripcion
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channel_id, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

        }
    }

    companion object {
        val name = "Notificacion"
        val descripcion = "Descripcion del canal"
        const val app_id = "com.example.seminario3pm"
        val channel_id = "${app_id}_c1"
        var id = AtomicInteger(0)

        fun createNotificationId(): Int {
            return id.incrementAndGet()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ejercicio1)
        
        createNotificationChannel()

        var boton = findViewById<Button>(R.id.ejercicio1)
        boton.setOnClickListener {
            mostrarNotificacion()
        }


    }

    private fun mostrarNotificacion() {
        var builder = NotificationCompat.Builder(this, channel_id)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(id.toString())
            .setContentText("Hola mundo")
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        with(NotificationManagerCompat.from(this)) {
            if (ActivityCompat.checkSelfPermission(
                    this@Ejercicio1,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {

            }
            notify(createNotificationId(), builder.build())
        }
    }
}