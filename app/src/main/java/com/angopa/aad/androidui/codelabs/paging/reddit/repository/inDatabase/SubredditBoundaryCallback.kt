package com.angopa.aad.androidui.codelabs.paging.reddit.repository.inDatabase

import androidx.annotation.MainThread
import androidx.paging.PagedList
import com.angopa.aad.androidui.codelabs.paging.reddit.api.RedditApi
import com.angopa.aad.androidui.codelabs.paging.reddit.util.PagingRequestHelper
import com.angopa.aad.androidui.codelabs.paging.reddit.util.createStatusLiveData
import com.angopa.aad.data.localdata.RedditPost
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
    private val handleResponse: (String, RedditApi.ListingResponse?) -> Unit,
    private val ioExecutor: Executor,
    private val networkPageSize: Int
) : PagedList.BoundaryCallback<RedditPost>() {

    val helper = PagingRequestHelper(ioExecutor)
    val networkState = helper.createStatusLiveData()

    /**
     * Database returned 0 items. We should query the backend for more items.
     */
    @MainThread
    override fun onZeroItemsLoaded() {
        helper.runIfNotRunning(PagingRequestHelper.RequestType.INITIAL) {
            webService.getTop(
                subreddit = subredditName,
                limit = networkPageSize
            ).enqueue(createWebserviceCallback(it))
        }
    }
    
    /**
     * User reached to the end of the list.
     */
    @MainThread
    override fun onItemAtEndLoaded(itemAtEnd: RedditPost) {
        helper.runIfNotRunning(PagingRequestHelper.RequestType.AFTER) {
            webService.getTopAfter(
                subreddit = subredditName,
                after = itemAtEnd.name,
                limit = networkPageSize
            ).enqueue(createWebserviceCallback(it))
        }
    }

    /**
     * every time it gets new items, boundary callback simply inserts them into the database and
     * paging library takes care of refreshing the list if necessary.
     */
    private fun insertItemsIntoDb(
        response: Response<RedditApi.ListingResponse>,
        it: PagingRequestHelper.Request.Callback
    ) {
        ioExecutor.execute {
            handleResponse(subredditName, response.body())
            it.recordSuccess()
        }
    }

    override fun onItemAtFrontLoaded(itemAtFront: RedditPost) {
        // ignored, since we only ever append to what's in the DB
    }

    private fun createWebserviceCallback(
        it: PagingRequestHelper.Request.Callback
    ): Callback<RedditApi.ListingResponse>? {
        return object : Callback<RedditApi.ListingResponse> {
            override fun onFailure(call: Call<RedditApi.ListingResponse>, t: Throwable) {
                it.recordFailure(t)
            }

            override fun onResponse(
                call: Call<RedditApi.ListingResponse>,
                response: Response<RedditApi.ListingResponse>
            ) {
                insertItemsIntoDb(response, it)
            }
        }
    }
}
