package com.angopa.aad.androidcore.codelabs.fundamentals.service

import android.app.IntentService
import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.angopa.aad.R
import com.angopa.aad.utilities.FOREGROUND_NOTIFICATION_CHANNEL_ID

class ForegroundService : IntentService("ForegroundService") {

    override fun onCreate() {
        super.onCreate()
        startForeground(FOREGROUND_ID, buildForegroundNotification())
    }

    override fun onHandleIntent(intent: Intent?) {
        try {
            Thread.sleep(5000)
        } catch (exception: InterruptedException) {
            Thread.currentThread().interrupt()
        }

        raiseNotificationAfterComplete()

        stopForeground(true)
    }

    private fun raiseNotificationAfterComplete() {
        val pendingIntent: PendingIntent =
            Intent(this, ServiceActivity::class.java).let { notificationIntent ->
                PendingIntent.getActivity(this, 0, notificationIntent, 0)
            }

        val builder = NotificationCompat.Builder(this, obtainNotificationChannel())

        builder
            .setAutoCancel(true)
            .setDefaults(Notification.DEFAULT_ALL)
            .setWhen(System.currentTimeMillis())
            .setContentTitle(getString(R.string.download_complete_notification_title))
            .setContentText(getString(R.string.download_complete_notification_description))
            .setSmallIcon(R.drawable.ic_notification_complete)
            .setContentIntent(pendingIntent)
            .setTicker(getString(R.string.notification_ticker))

        with(NotificationManagerCompat.from(this)) {
            notify(NOTIFY_ID, builder.build())
        }
    }

    private fun buildForegroundNotification(): Notification {
        val notification = NotificationCompat.Builder(this, obtainNotificationChannel())

        notification
            .setOngoing(true)
            .setContentTitle(getString(R.string.downloading_notification_title))
            .setContentText(getString(R.string.downloading_notification_description))
            .setSmallIcon(R.drawable.ic_notification_downloading)
            .setTicker(getString(R.string.notification_ticker))

        return notification.build()
    }

    private fun obtainNotificationChannel(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            FOREGROUND_NOTIFICATION_CHANNEL_ID
        } else {
            // If earlier version channel ID is not used
            // https://developer.android.com/reference/android/support/v4/app/NotificationCompat.Builder.html#NotificationCompat.Builder(android.content.Context)
            ""
        }
    }

    companion object {
        private const val NOTIFY_ID = 1337
        private const val FOREGROUND_ID = 1338
    }
}