package com.angopa.aad.androidui.codelabs.paging.firebase.posts.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

/**
 *  Created by Andres Gonzalez on 03/13/2020.
 */
@IgnoreExtraProperties
data class Entry(
    val caption: String? = null,
    val timestamp: Long? = null,
    val image: String? = null,
    val metrics: Metrics? = Metrics(),
    var postId: String? = null,
    val type: String? = null,
    val userId: String? = null
) {

    @Exclude
    fun toMap(): Map<String, Any?> = mapOf(
        "caption" to caption,
        "timestamp" to timestamp,
        "image" to image,
        "metrics" to metrics?.toMap(),
        "postId" to postId,
        "type" to type,
        "userId" to userId
    )

}