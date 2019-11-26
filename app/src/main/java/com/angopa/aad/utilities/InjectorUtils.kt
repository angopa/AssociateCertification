package com.angopa.aad.utilities

import android.content.Context
import com.angopa.aad.Storage
import com.angopa.aad.data.AppDatabase
import com.angopa.aad.data.LinkRepository
import com.angopa.aad.data.TabRepository
import com.angopa.aad.viewmodel.*

/**
 * Static methods used to inject classes needed for various Activities and Fragments
 */
object InjectorUtils {
    private fun getTabRepository(context: Context): TabRepository = TabRepository.getInstance(
        AppDatabase.getInstance(context.applicationContext).tabDao()
    )


    private fun getLinkRepository(context: Context): LinkRepository =
        LinkRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).linkDao()
        )


    fun provideAndroidCoreViewModelFactory(
        context: Context,
        tabId: String
    ): AndroidCoreViewModelFactory = AndroidCoreViewModelFactory(
        getTabRepository(context),
        getLinkRepository(context),
        tabId
    )


    fun provideAndroidUiViewModelFactory(
        context: Context,
        tabId: String
    ): AndroidUiViewModelFactory = AndroidUiViewModelFactory(
        getTabRepository(context),
        getLinkRepository(context),
        tabId
    )

    fun provideAndroidDataManagementViewModelFactory(
        context: Context,
        tabId: String
    ): AndroidDataManagementViewModelFactory = AndroidDataManagementViewModelFactory(
        getTabRepository(context),
        getLinkRepository(context),
        tabId
    )


    fun provideAndroidDebuggingViewModelFactory(
        context: Context,
        tabId: String
    ): AndroidDebuggingViewModelFactory = AndroidDebuggingViewModelFactory(
        getTabRepository(context),
        getLinkRepository(context),
        tabId
    )


    fun provideAndroidTestViewModelFactory(
        context: Context,
        tabId: String
    ): AndroidTestViewModelFactory = AndroidTestViewModelFactory(
        getTabRepository(context),
        getLinkRepository(context),
        tabId
    )


    fun provideMultiplePurposeActivityViewModelFactory(
        context: Context
    ): MultiplePurposeActivityViewModelFactory =
        MultiplePurposeActivityViewModelFactory(Storage.getInstance(context))
}