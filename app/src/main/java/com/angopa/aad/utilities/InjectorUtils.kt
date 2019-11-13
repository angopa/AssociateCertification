package com.angopa.aad.utilities

import android.content.Context
import com.angopa.aad.data.AppDatabase
import com.angopa.aad.data.LinkRepository
import com.angopa.aad.data.TabRepository
import com.angopa.aad.viewmodel.*

/**
 * Static methods used to inject classes needed for various Activities and Fragments
 */
object InjectorUtils {
    private fun getTabRepository(context: Context): TabRepository {
        return TabRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).tabDao()
        )
    }

    private fun getLinkRepository(context: Context): LinkRepository {
        return LinkRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).linkDao()
        )
    }

    fun provideAndroidCoreViewModelFactory(
        context: Context,
        tabId: String
    ): AndroidCoreViewModelFactory {
        return AndroidCoreViewModelFactory(
            getTabRepository(context),
            getLinkRepository(context),
            tabId
        )
    }

    fun provideAndroidUiViewModelFactory(
        context: Context,
        tabId: String
    ): AndroidUiViewModelFactory {
        return AndroidUiViewModelFactory(
            getTabRepository(context),
            getLinkRepository(context),
            tabId
        )
    }

    fun provideAndroidDataManagementViewModelFactory(
        context: Context,
        tabId: String
    ): AndroidDataManagementViewModelFactory {
        return AndroidDataManagementViewModelFactory(
            getTabRepository(context),
            getLinkRepository(context),
            tabId
        )
    }

    fun provideAndroidDebuggingViewModelFactory(
        context: Context,
        tabId: String
    ): AndroidDebuggingViewModelFactory {
        return AndroidDebuggingViewModelFactory(
            getTabRepository(context),
            getLinkRepository(context),
            tabId
        )
    }

    fun provideAndroidTestViewModelFactory(
        context: Context,
        tabId: String
    ): AndroidTestViewModelFactory {
        return AndroidTestViewModelFactory(
            getTabRepository(context),
            getLinkRepository(context),
            tabId
        )
    }
}