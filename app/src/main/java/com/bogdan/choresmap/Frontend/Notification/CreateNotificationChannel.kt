package com.bogdan.choresmap.Frontend.Notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            "chore_geofence",
            "Chore Notifications",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Notifications for chore geofencing events"
        }
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}