package com.angopa.aad.androidcore.codelabs.fundamentals.workmanager.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.angopa.aad.androidcore.codelabs.fundamentals.workmanager.DashboardFragment.Companion.KEY_DATA_URI
import com.angopa.aad.androidcore.codelabs.fundamentals.workmanager.DashboardFragment.Companion.KEY_IMAGE_RESULT
import timber.log.Timber

class SimpleWorkWithData(context: Context, parameters: WorkerParameters) :
    Worker(context, parameters) {
    override fun doWork(): Result {
        //Get the input
        val workData = inputData.getString(KEY_DATA_URI)

        Timber.d("Inside Worker, WorkData received: [ %s ]", workData)

        //Create the output of the work
        val outputData = workDataOf(KEY_IMAGE_RESULT to "Success")

        return Result.success(outputData)
    }
}