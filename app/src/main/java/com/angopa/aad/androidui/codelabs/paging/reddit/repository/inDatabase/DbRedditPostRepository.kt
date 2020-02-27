package com.angopa.aad.androidui.codelabs.paging.reddit.repository.inDatabase

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
        private const val DEFAULT_NETWORK_PAGE_SIZE = 10
    }

    /**
     * Returns a Listing for the given subreddit.
     */
//    override fun postsOfSubreddit(subReddit: String, pageSize: Int): Listing<RedditPost> {
        // create a boundary callback which will observe when the user reaches to the edges of
        // the list and update the database with extra data.
//        val boundaryCallback =
//        return Listing(
//            pagedList = livePagedList,
//            networkState = boundaryCallback.networkState,
//            retry = {
//                boundaryCallback.helper.retryAllFailed()
//            },
//            refresh = {
//                refreshTrigger.value = null
//            },
//            refreshState = refreshState
//        )
//    }

    /**
     * Inserts the response into the database while also assigning position indices to items.
     */
    private fun insertResultIntoDb(subRedditName: String, body: RedditApi.ListingResponse?) {
        body!!.data.children.let { posts ->
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

    /**
     * When refresh is called, we simply run a fresh network request and when it arrives, clear
     * the database table and insert all new items in a transaction.
     * <p>
     * Since the PagedList already uses a database bound data source, it will automatically be
     * updated after the database transaction is finished.
     */
    @MainThread
    private fun refres(subRedditName: String): LiveData<NetworkState> {
        val networkState = MutableLiveData<NetworkState>()
        networkState.value = LOADING
        redditApi.getTop(subRedditName, networkPageSize).enqueue(
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
//                            database.posts().deleteByRubreddit(subRedditName)
                            insertResultIntoDb(subRedditName, response.body())
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
