package com.angopa.aad

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.widget.Toast
import com.angopa.aad.receiver.AppMessagesReceiver

class WearApplication : Application() {
    private var appMessagesReceiver: BroadcastReceiver? = null

    override fun onCreate() {
        super.onCreate()
        val pendingIntent = IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        appMessagesReceiver = AppMessagesReceiver()
        registerReceiver(appMessagesReceiver, pendingIntent)
        Toast.makeText(this, "Broadcast Start", Toast.LENGTH_SHORT).show()
    }

    override fun onTerminate() {
        super.onTerminate()
        if (appMessagesReceiver != null) unregisterReceiver(appMessagesReceiver)
        Toast.makeText(this, "Broadcast Stop", Toast.LENGTH_SHORT).show()
    }
}