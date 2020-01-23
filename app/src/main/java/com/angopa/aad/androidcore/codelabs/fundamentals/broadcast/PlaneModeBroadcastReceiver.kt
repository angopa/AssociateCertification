package com.angopa.aad.androidcore.codelabs.fundamentals.broadcast

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.angopa.aad.R
import com.angopa.aad.utilities.REGULAR_NOTIFICATION_CHANNEL_ID

private const val NOTIFICATION_ID = 998

class BroadcastReceiverPlaneMode : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val isEnabled: Boolean = intent?.extras?.get("state") as Boolean
        StringBuilder().apply {
            append("Action: ${intent.action}\n")
            append("URI: ${intent.toUri(Intent.URI_INTENT_SCHEME)}\n")
            toString().also { log ->
                Toast.makeText(context, log, Toast.LENGTH_LONG).show()
            }
        }
        val builder = context?.let {
            NotificationCompat.Builder(it, REGULAR_NOTIFICATION_CHANNEL_ID)
                .setContentTitle(context.getString(R.string.notification_broadcast_receiver_plane_mode_title))
                .setContentText(context.getString(R.string.notification_broadcast_receiver_plane_mode_body))
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_airplanemode_active)
        }

        val notificationManager =
            context?.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (isEnabled) {
            notificationManager.notify(NOTIFICATION_ID, builder?.build())
        } else {
            notificationManager.cancel(NOTIFICATION_ID)
        }
    }
}