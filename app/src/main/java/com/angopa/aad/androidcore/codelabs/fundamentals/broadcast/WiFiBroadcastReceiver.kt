package com.angopa.aad.androidcore.codelabs.fundamentals.broadcast

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.net.wifi.WifiManager.WIFI_STATE_DISABLED
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.angopa.aad.R
import com.angopa.aad.utilities.REGULAR_NOTIFICATION_CHANNEL_ID

private const val NOTIFICATION_ID = 889

class WiFiBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val wifiState = intent?.extras?.get("wifi_state") as Int

        val isEnabled = WIFI_STATE_DISABLED == wifiState

        StringBuilder().apply {
            append("Action: ${intent.action}\n")
            append("URI: ${intent.toUri(Intent.URI_INTENT_SCHEME)}\n")
            toString().also { log ->
                Toast.makeText(context, log, Toast.LENGTH_LONG).show()
            }
        }

        val builder = context?.let {
            NotificationCompat.Builder(context, REGULAR_NOTIFICATION_CHANNEL_ID)
                .setContentTitle(context.getString(R.string.broadcast_notification_title))
                .setContentText(context.getString(R.string.broadcast_notification_body))
                .setSmallIcon(R.drawable.ic_wifi_disable)
                .setAutoCancel(true)
        }

        val notificationManager: NotificationManager =
            context?.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (isEnabled) {
            notificationManager.notify(NOTIFICATION_ID, builder?.build())
        } else {
            notificationManager.cancel(NOTIFICATION_ID)
        }
    }
}