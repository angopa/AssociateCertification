package com.angopa.aad.codelabs.fundamentals.workmanager.bluromaticcodelab

import android.content.Context
import android.graphics.BitmapFactory
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.angopa.aad.R
import com.angopa.aad.codelabs.fundamentals.workmanager.workers.WorkerUtils
import timber.log.Timber

class BlurWorker(context: Context, parameters: WorkerParameters) : Worker(context, parameters) {

    override fun doWork(): Result {
        val applicationContext = applicationContext

        return try {
            val picture =
                BitmapFactory.decodeResource(applicationContext.resources, R.drawable.test)

            //Blur the image
            val output = WorkerUtils.blurBitmap(picture, applicationContext)

            //Write bitmap to a temp file
            val outputUri = WorkerUtils.writeBitmapToFile(applicationContext, output)

            WorkerUtils.makeStatusNotification("Output is: $outputUri", applicationContext)

            //If there were no errors return SUCCESS
            Result.success()
        } catch (throwable: Throwable) {
            Timber.e(throwable)
            Result.failure()
        }
    }

}