package com.angopa.aad.codelabs.fundamentals.workmanager.bluromaticcodelab

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.text.TextUtils
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.angopa.aad.utilities.KEY_IMAGE_URI
import timber.log.Timber

class BlurWorker(context: Context, parameters: WorkerParameters) : Worker(context, parameters) {

    override fun doWork(): Result {
        WorkerUtils.sleep()
        val applicationContext = applicationContext

        return try {
            val resourceUri = inputData.getString(KEY_IMAGE_URI)

            if (TextUtils.isEmpty(resourceUri)) {
                Timber.d("Invalid input uri")
                throw IllegalArgumentException()
            }

            val contentResolve = getApplicationContext().contentResolver

            val picture =
                BitmapFactory.decodeStream(contentResolve.openInputStream(Uri.parse(resourceUri)))

            //Blur the image
            val output = WorkerUtils.blurBitmap(picture, applicationContext)

            //Write bitmap to a temp file
            val outputUri = WorkerUtils.writeBitmapToFile(applicationContext, output)

            val outputData = Data.Builder().putString(KEY_IMAGE_URI, outputUri.toString()).build()

            WorkerUtils.makeStatusNotification("Output is: $outputUri", applicationContext)

            //If there were no errors return SUCCESS
            Result.success(outputData)
        } catch (throwable: Throwable) {
            Timber.e(throwable)
            Result.failure()
        }
    }

}