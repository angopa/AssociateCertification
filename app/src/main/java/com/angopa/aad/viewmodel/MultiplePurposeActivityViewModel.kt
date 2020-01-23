package com.angopa.aad.viewmodel

import androidx.lifecycle.ViewModel
import com.angopa.aad.Storage
import com.angopa.aad.androidcore.codelabs.fundamentals.activity.ActivityModel

class MultiplePurposeActivityViewModel(
    private val storage: Storage
) : ViewModel() {
    val activityModel: ActivityModel = storage.getMultipleOptions()

    fun saveSelections(contactUri: String, imageUri: String) {
        storage.persistMultipleOptions(ActivityModel(contactUri, imageUri))
    }
}