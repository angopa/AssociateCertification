package com.angopa.aad.codelabs.fundamentals.workmanager.bluromaticcodelab

import android.app.Application
import android.net.Uri
import android.text.TextUtils
import androidx.lifecycle.AndroidViewModel
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

class BlurViewModel(application: Application) : AndroidViewModel(application) {
    private val workManager = WorkManager.getInstance(application)

    private var imageUri: Uri? = null

    /**
     * Create the WorkRequest to apply the blur and save the resulting image
     * @param blurLevel The amount to blur the image
     */
    fun applyBlur(blurLevel: Int) {
        workManager.enqueue(OneTimeWorkRequestBuilder<BlurWorker>().build())
    }

    /**
     * Setters
     */
    fun setImageUri(uri: String) {
        imageUri = uriOrNull(uri)
    }

    /**
     * Getters
     */
    fun getImageUri(): Uri? {
        return imageUri
    }

    private fun uriOrNull(uriString: String): Uri? {
        return if (!TextUtils.isEmpty(uriString)) {
            Uri.parse(uriString)
        } else null
    }
}