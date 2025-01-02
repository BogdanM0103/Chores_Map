package com.bogdan.choresmap.Frontend.Notification

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.bogdan.choresmap.R

fun sendNotification(context: Context, message: String) {
    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    val notificationBuilder = NotificationCompat.Builder(context, "chore_geofence")
//        .setSmallIcon()
        .setContentTitle("Chore Reminder")
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true)

    notificationManager.notify(1, notificationBuilder.build())
}