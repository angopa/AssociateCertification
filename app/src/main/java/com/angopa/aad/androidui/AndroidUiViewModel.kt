package com.angopa.aad.androidui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.angopa.aad.data.localdata.Link
import com.angopa.aad.data.localdata.LinkRepository
import com.angopa.aad.data.localdata.Tab
import com.angopa.aad.data.localdata.TabRepository

class AndroidUiViewModel(
    tabRepository: TabRepository,
    linkRepository: LinkRepository,
    tabId: String
) : ViewModel() {
    val tab: LiveData<Tab> = tabRepository.getTab(tabId)
    val links: LiveData<List<Link>> = linkRepository.getLinksForTab(tabId)
}