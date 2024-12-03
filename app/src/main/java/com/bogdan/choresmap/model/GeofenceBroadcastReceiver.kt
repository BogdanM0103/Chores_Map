package com.bogdan.choresmap.model

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent

class GeofenceBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        GeofencingEvent.fromIntent(intent)?.let { geofencingEvent ->
            if (geofencingEvent.hasError()) {
                Log.e("GeofenceReceiver", "Error code: ${geofencingEvent.errorCode}")
                return
            }

            val transition = geofencingEvent.geofenceTransition
            if (transition == Geofence.GEOFENCE_TRANSITION_ENTER || transition == Geofence.GEOFENCE_TRANSITION_EXIT) {
                geofencingEvent.triggeringGeofences?.forEach { geofence ->
                    Log.d("GeofenceReceiver", "Geofence triggered: ${geofence.requestId}")
                    // Trigger notification or handle geofence transition
                }
            }
        }
    }
}
