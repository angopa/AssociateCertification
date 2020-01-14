package com.angopa.aad.codelabs.fundamentals.workmanager.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import timber.log.Timber

class SimpleWork(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {
    override fun doWork(): Result {
        Timber.d("We are inside Worker")
        return Result.success()
    }
}