package com.angopa.aad.androidcore.codelabs.fundamentals.workmanager.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import kotlinx.coroutines.delay
import timber.log.Timber

class ProgressWorker(context: Context, parameters: WorkerParameters) :
    CoroutineWorker(context, parameters) {

    companion object {
        const val Progress = "Progress"
        private const val delayDuration = 5000L
    }

    override suspend fun doWork(): Result {
        val firstUpdate = workDataOf(Progress to 0)
        val lastUpdate = workDataOf(Progress to 100)
        Timber.d("Progress Worker start")
        setProgress(firstUpdate)
        delay(delayDuration)
        setProgress(lastUpdate)
        Timber.d("Progress Worker end")
        return Result.success()
    }
}