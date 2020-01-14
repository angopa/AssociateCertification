package com.angopa.aad.codelabs.fundamentals.workmanager.workers

import android.annotation.SuppressLint
import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class EchoWorker(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {


    @SuppressLint("RestrictedApi", "VisibleForTests")
    override fun doWork(): Result {
        return when (inputData.size()) {
            0 -> Result.failure()
            else -> Result.success(inputData)
        }
    }
}