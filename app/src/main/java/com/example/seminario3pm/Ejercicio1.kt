package com.example.seminario3pm

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
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
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channel_id, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

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

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ejercicio1)

        createNotificationChannel()

        var boton = findViewById<Button>(R.id.ejercicio1)
        boton.setOnClickListener {
            mostrarNotificacion()
        }


    }

    private fun mostrarNotificacion() {
        var principal = Intent(this, MainActivity::class.java)
        var pendingIntent =
            PendingIntent.getActivity(this, 0, principal, PendingIntent.FLAG_MUTABLE)

        var actividad = Intent(this, Ejercicio1::class.java)
        var pendingIntent2 =
            PendingIntent.getActivity(this, 0, actividad, PendingIntent.FLAG_MUTABLE)

        var builder = NotificationCompat.Builder(this, channel_id)
            .setSmallIcon(R.drawable.a)
            .setContentTitle("Mi ide es -> $id")
            .setContentText("Hola mundo")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .addAction(R.drawable.a, "Abrir actividad", pendingIntent2)



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