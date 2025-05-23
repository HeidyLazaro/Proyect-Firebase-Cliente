package net.heidylazaro.proyectfirebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d("FCMMessagge", "Mensaje: ${message.data}")

        showNotification(
            title = message.notification?.title ?: "Nuevo mensaje",
            body = message.notification?.body ?: "Tienes un nuevo mensaje"
        )
    }

    private fun showNotification(title: String, body: String) {
        val channelId = "default_channel"
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Canal por defecto",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.drawable.baseline_circle_notifications_24)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1, notification)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCMMessagge", "El nuevo token: $token")
    }
}
