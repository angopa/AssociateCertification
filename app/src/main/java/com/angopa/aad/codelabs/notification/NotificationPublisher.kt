package com.angopa.aad.codelabs.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import com.angopa.aad.R
import com.angopa.aad.codelabs.notification.NotificationDashboardFragment.Companion.KEY_TEXT_REPLY
import com.angopa.aad.utilities.REGULAR_NOTIFICATION_CHANNEL_ID

class NotificationPublisher : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        // Build a notification, which informs the yser that the system handled their interaction with the previous notification.
        val building = NotificationCompat.Builder(context!!, REGULAR_NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(context.getString(R.string.notification_title))
            .setContentText(context.getString(R.string.notification_reply_message))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setTimeoutAfter(NotificationDashboardFragment.CANCEL_TIMEOUT)
            .setAutoCancel(true)

        val id = intent?.getIntExtra(NOTIFICATION_ID, 0)

        with(NotificationManagerCompat.from(context)) {
            notify(id!!, building.build())
        }

        // Receive user input from the notification's reply UI
        val reply = RemoteInput.getResultsFromIntent(intent).getCharSequence(KEY_TEXT_REPLY)
        Toast.makeText(context, reply, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val NOTIFICATION_ID = "notification_id"
    }
}