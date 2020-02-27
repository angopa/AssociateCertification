package com.angopa.aad.androidui.codelabs.paging.cheesefactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.angopa.aad.data.localdata.CheeseRepository

/**
 *  Created by Andres Gonzalez on 02/18/2020.
 */
class CheeseViewModelFactory(
    private val application: Application,
    private val cheeseRepository: CheeseRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CheeseViewModel(
            application,
            cheeseRepository
        ) as T
    }
}