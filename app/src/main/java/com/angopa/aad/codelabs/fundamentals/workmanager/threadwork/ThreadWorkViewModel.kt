package com.angopa.aad.codelabs.fundamentals.workmanager.threadwork

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.angopa.aad.codelabs.fundamentals.workmanager.workers.CoroutineDownloadWorker
import com.angopa.aad.codelabs.fundamentals.workmanager.workers.DownloadWorker
import com.angopa.aad.codelabs.fundamentals.workmanager.workers.LongRunningWorker

class ThreadWorkViewModel(context: Context) : ViewModel() {
    private val workManager = WorkManager.getInstance(context)

    fun createWorker() {
        val request = OneTimeWorkRequestBuilder<DownloadWorker>()
            .build()

        workManager.enqueue(request)
    }

    fun createCoroutineWorker() {
        val request = OneTimeWorkRequestBuilder<CoroutineDownloadWorker>()
            .build()

        workManager.enqueue(request)
    }

    fun createLongRunningWorker() {
        val workData = workDataOf(
            LongRunningWorker.KEY_INPUT_URL to "https://www.google.com",
            LongRunningWorker.KEY_OUTPUT_FILE_NAME to "fileName"
        )

        val request = OneTimeWorkRequestBuilder<LongRunningWorker>()
            .setInputData(workData)
            .build()

        workManager.enqueue(request)
    }
}