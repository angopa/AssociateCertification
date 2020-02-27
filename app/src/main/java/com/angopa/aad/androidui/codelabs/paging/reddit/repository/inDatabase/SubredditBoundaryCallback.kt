package com.angopa.aad.androidui.codelabs.paging.reddit.repository.inDatabase

import androidx.paging.PagedList
import com.angopa.aad.androidui.codelabs.paging.reddit.api.RedditApi
import com.angopa.aad.data.localdata.RedditPost
import java.util.concurrent.Executor

/**
 *  Created by Andres Gonzalez on 02/24/2020.
 *
 * This boundary callback gets notified when user reaches to the edges of the list such that the
 * database cannot provide any more data.
 * <p>
 * The boundary callback might be called multiple times for the same direction so it does its own
 * rate limiting using the PagingRequestHelper class.
 */
class SubredditBoundaryCallback(
    private val subredditName: String,
    private val webService: RedditApi,
    private val handleResponse: (String, RedditApi.ListingResponse) -> Unit?,
    private val ioExecutor: Executor,
    private val networkPageSize: Int
) : PagedList.BoundaryCallback<RedditPost>() {

}
