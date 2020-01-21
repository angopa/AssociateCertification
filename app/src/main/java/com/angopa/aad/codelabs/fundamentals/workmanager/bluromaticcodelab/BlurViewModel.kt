package com.angopa.aad.codelabs.fundamentals.workmanager.bluromaticcodelab

import android.app.Application
import android.net.Uri
import android.text.TextUtils
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.work.*
import com.angopa.aad.utilities.KEY_IMAGE_URI
import timber.log.Timber

class BlurViewModel(application: Application) : AndroidViewModel(application) {
    private val workManager = WorkManager.getInstance(application)

    private var imageUri: Uri? = null

    private var savedWorkInfo: LiveData<List<WorkInfo>>

    init {
        savedWorkInfo = workManager.getWorkInfosByTagLiveData(TAG_OUTPUT)
    }

    private var blurOutputUri: Uri? = null

    /**
     * Create the WorkRequest to apply the blur and save the resulting image
     * @param blurLevel The amount to blur the image
     */
    fun applyBlur(blurLevel: Int) {
        // Add WorkRequest to Cleanup temporary images
        var workContinuation =
            workManager.beginUniqueWork(
                IMAGE_MANIPULATION_WORK_NAME,
                ExistingWorkPolicy.REPLACE,
                OneTimeWorkRequest.from(CleanupWorker::class.java)
            )

        // Add WorkRequest to blur the image the number of times requested
        for (x in 0..blurLevel) {
            val blurRequest =
                OneTimeWorkRequestBuilder<BlurWorker>()
                    .setInputData(createInputDataForUri())

            // Input the Uri if this is the first blur operation
            // after the first blur operation the input will be the output of previous
            // blur operation
            if (x == 0) {
                blurRequest.setInputData(createInputDataForUri())
            }

            workContinuation = workContinuation.then(blurRequest.build())
        }

        // Add worker to save the image to the filesystem
        val save = OneTimeWorkRequest.Builder(SaveImageToFileWorker::class.java)
            .addTag(TAG_OUTPUT)
            .build()
        workContinuation = workContinuation.then(save)

        // Start the work
        workContinuation.enqueue()
    }

    /**
     * Cancel work using the work's unique name
     */
    fun cancelWork() {
        workManager.cancelUniqueWork(IMAGE_MANIPULATION_WORK_NAME)
    }

    /**
     * Setters
     */
    fun setImageUri(uri: String) {
        imageUri = uriOrNull(uri)
    }

    fun setBlurOutputUri(blurOutputUri: String) {
        this.blurOutputUri = uriOrNull(blurOutputUri)
    }

    /**
     * Getters
     */
    fun getImageUri() = imageUri

    fun getBlurOutputUri() = blurOutputUri

    fun getOutputWorkInfo(): LiveData<List<WorkInfo>> {
        return savedWorkInfo
    }

    /**
     * Creates the input data bundle which includes the Uri to operate on @return Data
     * which contains the Image Uri as a String
     */
    private fun createInputDataForUri(): Data {
        val dataBuilder = Data.Builder()
        if (imageUri != null) {
            dataBuilder.putString(KEY_IMAGE_URI, imageUri.toString())
        }
        return dataBuilder.build()
    }

    private fun uriOrNull(uriString: String): Uri? {
        return if (!TextUtils.isEmpty(uriString)) {
            Uri.parse(uriString)
        } else null
    }

    companion object {
        const val IMAGE_MANIPULATION_WORK_NAME = "unique_work"
        const val TAG_OUTPUT = "TAG_OUTPUT"
    }
}
