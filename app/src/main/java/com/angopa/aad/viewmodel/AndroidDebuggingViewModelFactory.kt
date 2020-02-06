package com.angopa.aad.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.angopa.aad.data.localdata.LinkRepository
import com.angopa.aad.data.localdata.TabRepository

/**
 * Factory for creating a [AndroidDebuggingViewModel] with a constructor that takes a [TabRepository],
 * a [LinkRepository] and a ID for the current [Tab]
 */
class AndroidDebuggingViewModelFactory(
    private val tabRepository: TabRepository,
    private val linkRepository: LinkRepository,
    private val tabId: String
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AndroidDebuggingViewModel(tabRepository, linkRepository, tabId) as T
    }
}