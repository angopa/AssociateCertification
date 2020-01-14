package com.angopa.aad.codelabs.fundamentals.threads.jobscheduler

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.angopa.aad.MainActivity
import com.angopa.aad.R
import com.angopa.aad.utilities.PRIMARY_CHANNEL_ID

class NotificationJobService : JobService() {

    private lateinit var notificationManager: NotificationManager

    override fun onStartJob(params: JobParameters?): Boolean {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
            .setContentTitle(getString(R.string.notification_title_job_scheduler))
            .setContentText(getString(R.string.notification_body_job_scheduler))
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_job_running)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setAutoCancel(true)

        notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, builder.build())

        return false
    }

    override fun onStopJob(params: JobParameters?): Boolean = true
}
