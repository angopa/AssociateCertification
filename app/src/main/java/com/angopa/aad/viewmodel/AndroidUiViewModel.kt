package com.angopa.aad.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.angopa.aad.data.Link
import com.angopa.aad.data.LinkRepository
import com.angopa.aad.data.Tab
import com.angopa.aad.data.TabRepository

class AndroidUiViewModel(
    tabRepository: TabRepository,
    linkRepository: LinkRepository,
    tabId: String
) : ViewModel() {
    val tab: LiveData<Tab> = tabRepository.getTab(tabId)
    val links: LiveData<List<Link>> = linkRepository.getLinksForTab(tabId)
}