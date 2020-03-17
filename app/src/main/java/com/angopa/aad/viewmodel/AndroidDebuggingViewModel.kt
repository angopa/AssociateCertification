package com.angopa.aad.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.angopa.aad.data.localdata.model.Link
import com.angopa.aad.data.localdata.LinkRepository
import com.angopa.aad.data.localdata.model.Tab
import com.angopa.aad.data.localdata.TabRepository

class AndroidDebuggingViewModel(
    tabRepository: TabRepository,
    linkRepository: LinkRepository,
    tabId: String
) : ViewModel() {
    val tab: LiveData<Tab> = tabRepository.getTab(tabId)
    val links: LiveData<List<Link>> = linkRepository.getLinksForTab(tabId)
}
