package com.angopa.aad.codelabs.fundamentals.workmanager.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.*
import timber.log.Timber

class CoroutineDownloadWorker(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {
    private var index = 0

    override val coroutineContext: CoroutineDispatcher = Dispatchers.IO

    override suspend fun doWork(): Result = coroutineScope {
        val jobs = (0 until 100).map {
            async {
                downloadSynchronously("https://www.google.com")
            }
        }
        jobs.awaitAll()
        Result.success()
    }

    private fun downloadSynchronously(url: String) {
        index++
        Timber.d("Downloading $url $index")
        Thread.sleep(1000)
    }
}