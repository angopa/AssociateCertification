package com.angopa.aad.androidcore.codelabs.fundamentals.workmanager.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import timber.log.Timber
import java.io.IOException

class DownloadWorker(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {
    override fun doWork(): Result {
        for (i in 0..99) {
            if (isStopped) {
                break
            }

            try {
                downloadSynchronously("https://www.google.com $i")
            } catch (e: IOException) {
                return Result.failure()
            }
        }
        return Result.success()
    }

    private fun downloadSynchronously(url: String) {
        Timber.d("Downloading $url")
        Thread.sleep(1000)
    }

}