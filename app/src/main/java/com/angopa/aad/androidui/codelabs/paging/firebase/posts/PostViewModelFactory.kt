package com.angopa.aad.androidui.codelabs.paging.firebase.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.DatabaseReference

/**
 *  Created by Andres Gonzalez on 03/13/2020.
 */
class PostViewModelFactory(
    private val databaseReference: DatabaseReference
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FirebaseRealtimeDatabaseViewModel(
            databaseReference
        ) as T
    }
}