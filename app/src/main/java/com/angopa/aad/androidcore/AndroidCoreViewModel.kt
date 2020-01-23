package com.angopa.aad.androidcore

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.angopa.aad.data.Link
import com.angopa.aad.data.LinkRepository
import com.angopa.aad.data.Tab
import com.angopa.aad.data.TabRepository

class AndroidCoreViewModel internal constructor(
    tabRepository: TabRepository,
    linkRepository: LinkRepository,
    tabId: String
) : ViewModel() {
    val tab: LiveData<Tab> = tabRepository.getTab(tabId)
    val links: LiveData<List<Link>> = linkRepository.getLinksForTab(tabId)
}