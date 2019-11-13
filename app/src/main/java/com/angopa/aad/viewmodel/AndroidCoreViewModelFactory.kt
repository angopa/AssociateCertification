package com.angopa.aad.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.angopa.aad.data.LinkRepository
import com.angopa.aad.data.TabRepository

/**
 * Factory for creating a [CoreAndroidViewModel] with a constructor that takes a [TabRepository]
 * and a ID for the current [Tab]
 */
class AndroidCoreViewModelFactory(
    private val tabRepository: TabRepository,
    private val linkRepository: LinkRepository,
    private val tabId: String
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AndroidCoreViewModel(tabRepository, linkRepository, tabId) as T
    }
}
