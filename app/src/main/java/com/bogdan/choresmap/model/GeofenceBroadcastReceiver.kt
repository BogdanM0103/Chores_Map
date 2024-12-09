package com.bogdan.choresmap.model

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofenceStatusCodes
import com.google.android.gms.location.GeofencingEvent

class GeofenceBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val geofencingEvent = GeofencingEvent.fromIntent(intent)

        Log.d("GeofenceBroadcastReceiver", "Geofence event received")

        if (geofencingEvent != null) {
            if (geofencingEvent.hasError()) {
                Log.e("GeofenceBroadcastReceiver", "Geofence error: ${geofencingEvent.errorCode}")
                val errorMessage = GeofenceStatusCodes
                    .getStatusCodeString(geofencingEvent.errorCode)
                Log.e(TAG, errorMessage)
                return
            }
        }

        val geofenceTransition = geofencingEvent?.geofenceTransition


        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER ||
            geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {
            // Handle geofence transition
            val triggeringGeofences = geofencingEvent.triggeringGeofences

            if (triggeringGeofences != null) {
                for (geofence in triggeringGeofences) {
                    Log.i("GeofenceBroadcastReceiver", "Geofence triggered: ${geofence.requestId}")
                    // You can add notification logic here
                }
            }
        } else {
            Log.e("GeofenceBroadcastReceiver", "Here")
        }
    }
}