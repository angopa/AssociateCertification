package com.angopa.aad.androidui.codelabs.paging.reddit.repository.inDatabase

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.paging.toLiveData
import com.angopa.aad.androidui.codelabs.paging.reddit.api.RedditApi
import com.angopa.aad.androidui.codelabs.paging.reddit.db.RedditDb
import com.angopa.aad.androidui.codelabs.paging.reddit.repository.NetworkState
import com.angopa.aad.androidui.codelabs.paging.reddit.repository.NetworkState.Companion.LOADED
import com.angopa.aad.androidui.codelabs.paging.reddit.repository.NetworkState.Companion.LOADING
import com.angopa.aad.data.localdata.RedditPost
import com.angopa.aad.utilities.Listing
import com.angopa.aad.utilities.RedditPostRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor

/**
 *  Created by Andres Gonzalez on 02/23/2020.
 *
 * Repository implementation that uses a database PagedList + a boundary callback to return a
 * listing that loads in pages.
 */
class DbRedditPostRepository(
    val database: RedditDb,
    private val redditApi: RedditApi,
    private val ioExecutor: Executor,
    private val networkPageSize: Int = DEFAULT_NETWORK_PAGE_SIZE
) : RedditPostRepository {
    companion object {
        private const val DEFAULT_NETWORK_PAGE_SIZE = 20
    }

    /**
     * Returns a Listing for the given subreddit.
     */
    @MainThread
    override fun postsOfSubreddit(subReddit: String, pageSize: Int): Listing<RedditPost> {
        // create a boundary callback which will observe when the user reaches to the edges of
        // the list and update the database with extra data.
        val boundaryCallback = SubredditBoundaryCallback(
            webService = redditApi,
            subredditName = subReddit,
            handleResponse = this::insertResultIntoDb,
            ioExecutor = ioExecutor,
            networkPageSize = networkPageSize
        )
        // we are using a mutable live data to trigger refresh requests which eventually calls
        // refresh method and gets a new live data. Each refresh request by the user becomes a newly
        // dispatched data in refreshTrigger
        val refreshTrigger = MutableLiveData<Unit>()
        val refreshState = refreshTrigger.switchMap {
            refresh(subReddit)
        }

        // We use toLiveData Kotlin extension function here, you could also use LivePagedListBuilder
        val livePagedList = database.posts().postsBySubreddit(subReddit).toLiveData(
            pageSize = pageSize,
            boundaryCallback = boundaryCallback
        )

        return Listing(
            pagedList = livePagedList,
            networkState = boundaryCallback.networkState,
            retry = {
                boundaryCallback.helper.retryAllFailed()
            },
            refresh = {
                refreshTrigger.value = null
            },
            refreshState = refreshState
        )
    }

    /**
     * Inserts the response into the database while also assigning position indices to items.
     */
    private fun insertResultIntoDb(subRedditName: String, body: RedditApi.ListingResponse?) {
        body?.let {
            it.data.children.let { posts ->
                database.runInTransaction {
                    val start = database.posts().getNextIndexInSubreddit(subRedditName)
                    val items = posts.mapIndexed { index, child ->
                        child.data.indexInResponse = start + index
                        child.data
                    }
                    database.posts().insert(items)
                }
            }
        }
    }

    /**
     * When refresh is called, we simply run a fresh network request and when it arrives, clear
     * the database table and insert all new items in a transaction.
     * <p>
     * Since the PagedList already uses a database bound data source, it will automatically be
     * updated after the database transaction is finished.
     */
    @MainThread
    private fun refresh(subredditName: String): LiveData<NetworkState> {
        val networkState = MutableLiveData<NetworkState>()
        networkState.value = LOADING
        redditApi.getTop(subredditName, networkPageSize).enqueue(
            object : Callback<RedditApi.ListingResponse> {
                override fun onFailure(call: Call<RedditApi.ListingResponse>, t: Throwable) {
                    // retrofit calls this on main thread so safe to call set value
                    networkState.value = NetworkState.error(t.message)
                }

                override fun onResponse(
                    call: Call<RedditApi.ListingResponse>,
                    response: Response<RedditApi.ListingResponse>
                ) {
                    ioExecutor.execute {
                        database.runInTransaction {
                            database.posts().deleteBySubreddit(subredditName)
                            insertResultIntoDb(subredditName, response.body())
                        }
                        // since we are in bg thread now, post the result.
                        networkState.postValue(LOADED)
                    }
                }
            }
        )
        return networkState
    }
}
