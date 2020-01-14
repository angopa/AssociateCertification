package com.angopa.aad.codelabs.fundamentals.workmanager.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class ListenableSleepWorker(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {
    companion object {
        const val SLEEP_DURATION = "SLEEP_DURATION"
    }

    override suspend fun doWork(): Result {
        // Sleep in background thread
        val sleepDuration = inputData.getLong(SLEEP_DURATION, 1000)
        Thread.sleep(sleepDuration)
        return Result.success()
    }
}