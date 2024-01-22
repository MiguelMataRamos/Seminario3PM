package com.example.seminario3pm

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.util.concurrent.atomic.AtomicInteger

class Ejercicio2 : AppCompatActivity() {

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = Ejercicio2.name
            val descriptionText = Ejercicio1.descripcion
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(Ejercicio1.channel_id, name, importance).apply {
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
        setContentView(R.layout.activity_ejercicio2)

        createNotificationChannel()

        var boton = findViewById<Button>(R.id.btnNotificacion1)
        var boton2 = findViewById<Button>(R.id.btnNotificacion2)

        boton.setOnClickListener {
            mostrarNotificacionBigPicture()
        }

        boton2.setOnClickListener {
            mostrarNotificacionBigText()
        }

    }

    private fun mostrarNotificacionBigPicture() {
        var mybitmap = BitmapFactory.decodeResource(resources, R.drawable.paquirr_n)

        var builder = NotificationCompat.Builder(this, Ejercicio2.channel_id)
            .setSmallIcon(R.drawable.a)
            .setContentTitle("Mi ide es -> ${Ejercicio1.id}")
            .setContentText("Mi estilo es BigPicture")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setStyle(
                NotificationCompat.BigPictureStyle()
                    .bigPicture(mybitmap)
            )


        with(NotificationManagerCompat.from(this)) {
            if (ActivityCompat.checkSelfPermission(
                    this@Ejercicio2,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {

            }
            notify(Ejercicio1.createNotificationId(), builder.build())
        }
    }

    private fun mostrarNotificacionBigText() {

        var builder = NotificationCompat.Builder(this, Ejercicio2.channel_id)
            .setSmallIcon(R.drawable.a)
            .setContentTitle("Mi ide es -> ${Ejercicio1.id}")
            .setContentText("Mi estilo es BigText")
            .setPriority(NotificationCompat.PRIORITY_HIGH).setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Mi estilo es BigText y este es el texto que se muestra")
            )



        with(NotificationManagerCompat.from(this)) {
            if (ActivityCompat.checkSelfPermission(
                    this@Ejercicio2,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {

            }
            notify(Ejercicio1.createNotificationId(), builder.build())
        }
    }


}