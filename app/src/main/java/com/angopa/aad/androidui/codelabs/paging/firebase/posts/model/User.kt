package com.angopa.aad.androidui.codelabs.paging.firebase.posts.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

/**
 *  Created by Andres Gonzalez on 03/13/2020.
 */
@IgnoreExtraProperties
data class User(
    var id: String? = null,
    val username: String? = null,
    val email: String? = null
) {
    @Exclude
    fun toMap(): Map<String, Any?> = mapOf(
        "username" to username,
        "email" to email
    )
}