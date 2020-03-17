package com.angopa.aad.utilities

import android.content.Context
import com.angopa.aad.Application
import com.angopa.aad.Storage
import com.angopa.aad.androidcore.AndroidCoreViewModelFactory
import com.angopa.aad.androiddata.AndroidDataManagementViewModelFactory
import com.angopa.aad.androidui.AndroidUiViewModelFactory
import com.angopa.aad.androidui.codelabs.paging.cheesefactory.CheeseViewModelFactory
import com.angopa.aad.androidui.codelabs.paging.firebase.posts.PostViewModelFactory
import com.angopa.aad.androidui.codelabs.paging.social.PagingViewModelFactory
import com.angopa.aad.data.localdata.*
import com.angopa.aad.viewmodel.AndroidDebuggingViewModelFactory
import com.angopa.aad.viewmodel.AndroidTestViewModelFactory
import com.angopa.aad.viewmodel.MultiplePurposeActivityViewModelFactory
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

/**
 * Static methods used to inject classes needed for various Activities and Fragments
 */
object InjectorUtils {
    private fun getTabRepository(context: Context): TabRepository =
        TabRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).tabDao()
        )

    private fun getLinkRepository(context: Context): LinkRepository =
        LinkRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).linkDao()
        )

    private fun getPostRepository(context: Context): PostRepository =
        PostRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).postDao()
        )

    private fun getCheeseRepository(context: Context): CheeseRepository =
        CheeseRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).cheeseDao()
        )

    private fun getFirebaseDatabaseReference(): DatabaseReference =
        FirebaseDatabase.getInstance().reference

    fun provideAndroidCoreViewModelFactory(
        context: Context,
        tabId: String
    ): AndroidCoreViewModelFactory =
        AndroidCoreViewModelFactory(
            getTabRepository(context),
            getLinkRepository(context),
            tabId
        )

    fun provideAndroidUiViewModelFactory(
        context: Context,
        tabId: String
    ): AndroidUiViewModelFactory =
        AndroidUiViewModelFactory(
            getTabRepository(context),
            getLinkRepository(context),
            tabId
        )

    fun provideAndroidDataManagementViewModelFactory(
        context: Context,
        tabId: String
    ): AndroidDataManagementViewModelFactory =
        AndroidDataManagementViewModelFactory(
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

    fun providePagingViewModelFactory(
        context: Context
    ): PagingViewModelFactory =
        PagingViewModelFactory(
            getPostRepository(context)
        )

    fun provideCheeseViewModelFactory(
        context: Context,
        application: Application
    ): CheeseViewModelFactory =
        CheeseViewModelFactory(
            application,
            getCheeseRepository(context)
        )

    fun provideRealtimeViewModelFactory(): PostViewModelFactory =
        PostViewModelFactory(
            getFirebaseDatabaseReference()
        )
}