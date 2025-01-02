package com.bogdan.choresmap.Backend.Receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import com.bogdan.choresmap.Frontend.Notification.sendNotification
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofenceStatusCodes
import com.google.android.gms.location.GeofencingEvent

class GeofenceBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("GeofenceBroadcastReceiver", "Received geofence event")

        val geofencingEvent = intent?.let { GeofencingEvent.fromIntent(it) }
        if (geofencingEvent == null) {
            Log.e("GeofenceBroadcastReceiver", "GeofencingEvent is null")
            return
        }

        if (geofencingEvent.hasError()) {
            val errorCode = geofencingEvent.errorCode
            val errorMessage = GeofenceStatusCodes.getStatusCodeString(errorCode)
            Log.e("GeofenceBroadcastReceiver", "Error: $errorMessage")
            return
        }

        val geofenceTransition = geofencingEvent.geofenceTransition
        Log.d("GeofenceBroadcastReceiver", "Geofence transition: $geofenceTransition")

        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
            val triggeringGeofences = geofencingEvent.triggeringGeofences
            if (triggeringGeofences.isNullOrEmpty()) {
                Log.e("GeofenceBroadcastReceiver", "No geofences triggered")
                return
            }

            for (geofence in triggeringGeofences) {
                Log.i("GeofenceBroadcastReceiver", "Geofence triggered: ${geofence.requestId}")
                if (context != null) {
                    sendNotification(context, "Notification works")
                }
            }
        } else {
            Log.e("GeofenceBroadcastReceiver", "Unhandled transition: $geofenceTransition")
        }
    }
}