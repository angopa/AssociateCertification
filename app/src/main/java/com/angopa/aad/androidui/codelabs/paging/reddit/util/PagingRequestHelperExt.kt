package com.angopa.aad.androidui.codelabs.paging.reddit.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.angopa.aad.androidui.codelabs.paging.reddit.repository.NetworkState
import com.angopa.aad.androidui.codelabs.paging.reddit.repository.NetworkState.Companion.LOADED

/**
 *  Created by Andres Gonzalez on 03/05/2020.
 */
private fun getErrorMessage(report: PagingRequestHelper.StatusReport): String =
    PagingRequestHelper.RequestType.values().mapNotNull {
        report.getErrorFor(it)?.message
    }.first()


fun PagingRequestHelper.createStatusLiveData(): LiveData<NetworkState> {
    val liveData = MutableLiveData<NetworkState>()
    addListener { report ->
        when {
            report.hasRunning() -> liveData.postValue(NetworkState.LOADING)
            report.hasError() -> liveData.postValue(
                error(getErrorMessage(report))
            )
            else -> liveData.postValue(LOADED)
        }
    }
    return liveData
}