package com.angopa.aad.codelabs.fundamentals.workmanager.bluromaticcodelab

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.angopa.aad.utilities.KEY_IMAGE_URI
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class SaveImageToFileWorker(context: Context, parameters: WorkerParameters) :
    Worker(context, parameters) {

    override fun doWork(): Result {
        WorkerUtils.sleep()
        val contentResolver = applicationContext.contentResolver
        return try {
            val resourceUri = inputData.getString(KEY_IMAGE_URI)
            val bitmap =
                BitmapFactory.decodeStream(contentResolver.openInputStream(Uri.parse(resourceUri)))

            val outputUri = MediaStore.Images.Media.insertImage(
                contentResolver,
                bitmap,
                TITLE,
                DATE_FORMATTER.format(Date())
            )

            val outputData = Data.Builder().putString(KEY_IMAGE_URI, outputUri).build()
            Result.success(outputData)
        } catch (exception: Exception) {
            Timber.e("Unable to save image to Gallery, ${exception.printStackTrace()}")
            Result.failure()
        }

    }

    companion object {
        const val TITLE = "Blurred Image"
        val DATE_FORMATTER = SimpleDateFormat("yyy.MM.dd 'at' HH:mm:ss z", Locale.getDefault())
    }
}