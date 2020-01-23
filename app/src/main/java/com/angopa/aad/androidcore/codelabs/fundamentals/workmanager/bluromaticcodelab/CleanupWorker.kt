package com.angopa.aad.androidcore.codelabs.fundamentals.workmanager.bluromaticcodelab

import android.content.Context
import android.text.TextUtils
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.angopa.aad.utilities.OUTPUT_PATH
import timber.log.Timber
import java.io.File

class CleanupWorker(context: Context, parameters: WorkerParameters) : Worker(context, parameters) {
    override fun doWork(): Result {
        WorkerUtils.sleep()
        return try {
            val outputDirectory = File(applicationContext.filesDir, OUTPUT_PATH)
            if (outputDirectory.exists()) {
                val entries: Array<File>? = outputDirectory.listFiles()
                if (entries != null && entries.isNotEmpty()) {
                    for (entry in entries) {
                        val name = entry.name
                        if (!TextUtils.isEmpty(name) && name.endsWith(".png")) {
                            val deleted = entry.delete()
                            Timber.d("Deleted $name - $deleted")
                        }
                    }
                }
            }

            Result.success()
        } catch (exception: Exception) {
            Timber.e(exception)
            Result.failure()
        }
    }
}