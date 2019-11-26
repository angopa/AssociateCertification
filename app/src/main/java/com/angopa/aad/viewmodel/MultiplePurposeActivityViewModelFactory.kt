package com.angopa.aad.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.angopa.aad.Storage

/**
 * Factory for creating a [MultiplePurposeActivityViewModel] with a constructor that takes [Storage]
 */
class MultiplePurposeActivityViewModelFactory(
    private val storage: Storage
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MultiplePurposeActivityViewModel(storage) as T
    }
}