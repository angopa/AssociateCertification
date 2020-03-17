package com.angopa.aad.androidui.codelabs.paging.firebase.posts.model

import com.google.firebase.database.Exclude

/**
 *  Created by Andres Gonzalez on 03/13/2020.
 */
data class Metrics(
    val likes: Long? = null
) {
    @Exclude
    fun toMap(): Map<String, Any?> = mapOf(
        "likes" to likes
    )
}