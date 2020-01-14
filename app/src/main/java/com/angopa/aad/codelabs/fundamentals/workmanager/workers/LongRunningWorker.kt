package com.angopa.aad.codelabs.fundamentals.workmanager.workers

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.angopa.aad.R
import com.angopa.aad.utilities.REGULAR_NOTIFICATION_CHANNEL_ID
import timber.log.Timber

class LongRunningWorker(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {
    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    override suspend fun doWork(): Result {
        val inputUrl = inputData.getString(KEY_INPUT_URL) ?: return Result.failure()
        val outputFile = inputData.getString(KEY_OUTPUT_FILE_NAME) ?: return Result.failure()

        // Mark the worker as important
        val progress = "Starting Download"
        setForeground(createForegroundInfo(progress))
        download(inputUrl, outputFile)
        return Result.success()
    }

    private fun download(inputUrl: String, outputFile: String) {
        // Download the file and update bytes read, Calls setForegroundInfo() periodically
        // when it needs to update the ongoing Notification
        for (i in 0..100) {
            Timber.d("Saving $inputUrl data into $outputFile - $i%")
            Thread.sleep(1000)
        }
        notificationManager.cancel(0)
    }

    // Creates an instance of ForegroundNotification which can be used to update the ongoing notification
    private fun createForegroundInfo(progress: String): ForegroundInfo {
        val id = REGULAR_NOTIFICATION_CHANNEL_ID
        val title = applicationContext.getString(R.string.notification_title)
        val cancel = applicationContext.getString(R.string.dialog_label_cancel)

        // This pending Intent can be used to cancel the worker
        val intent =
            WorkManager.getInstance(applicationContext).createCancelPendingIntent(getId())

        val notification =
            NotificationCompat.Builder(applicationContext, id)
                .setContentTitle(title)
                .setTicker(title)
                .setContentText(progress)
                .setSmallIcon(R.drawable.ic_download)
                .setOngoing(true)
                //Add the Cancel action to the notification which can be used to cancel the worker
                .addAction(android.R.drawable.ic_delete, cancel, intent)
                .build()

        notificationManager.notify(0, notification)

        return ForegroundInfo(0, notification)
    }


    companion object {
        const val KEY_INPUT_URL = "KEY_INPUT_URL"
        const val KEY_OUTPUT_FILE_NAME = "KEY_OUTPUT_FILE_NAME"
    }
}