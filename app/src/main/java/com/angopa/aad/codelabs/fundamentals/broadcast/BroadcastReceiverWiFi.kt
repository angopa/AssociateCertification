package com.angopa.aad.codelabs.fundamentals.broadcast

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.net.wifi.WifiManager.WIFI_STATE_DISABLED
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.angopa.aad.R
import com.angopa.aad.utilities.REGULAR_NOTIFICATION_CHANNEL_ID

private const val NOTIFICATION_ID = 889

class BroadcastReceiverWiFi : BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.M)
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
                .setContentTitle("Wi-Fi")
                .setContentText("Wi Fi is disabled, app might not work correctly.")
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