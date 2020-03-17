package com.angopa.aad.androidui.codelabs.paging.firebase.posts

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.angopa.aad.R
import com.angopa.aad.androidui.codelabs.paging.firebase.posts.model.Entry
import com.angopa.aad.databinding.ListItemEntryBinding
import com.firebase.ui.database.paging.DatabasePagingOptions
import com.firebase.ui.database.paging.FirebaseRecyclerPagingAdapter
import com.firebase.ui.database.paging.LoadingState
import com.google.firebase.database.DatabaseError

/**
 *  Created by Andres Gonzalez on 03/13/2020.
 */
class PostAdapter(
    options: DatabasePagingOptions<Entry>,
    private val callback: (loading: Boolean, state: String) -> Unit
) : FirebaseRecyclerPagingAdapter<Entry, RecyclerView.ViewHolder>(options) {
    private lateinit var binding: ListItemEntryBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_item_entry,
            parent,
            false
        )
        return PostViewHolder(
            binding.root,
            parent.context
        )
    }

    override fun onLoadingStateChanged(state: LoadingState) {
        when (state) {
            LoadingState.LOADING_INITIAL -> callback(true, "LOADING_INITIAL")
            LoadingState.LOADING_MORE -> callback(true, "LOADING_MORE")
            LoadingState.LOADED -> callback(false, "LOADED")
            LoadingState.FINISHED -> callback(false, "FINISHED")
            LoadingState.ERROR -> retry()
        }
    }

    override fun onError(databaseError: DatabaseError) {
        callback(false, "")
        super.onError(databaseError)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int, item: Entry) {
        (viewHolder as PostViewHolder).bind(item)
    }

    class PostViewHolder(
        item: View,
        private val context: Context
    ) : RecyclerView.ViewHolder(item) {
        private val username = item.findViewById<TextView>(R.id.username_text_view)
        private val caption = item.findViewById<TextView>(R.id.caption_text_view)
        private val likes = item.findViewById<TextView>(R.id.likes_text_view)

        fun bind(item: Entry) {
            username.text = item.userId
            caption.text = item.caption
            likes.text = String.format(
                context.getString(R.string.label_firebase_entry_likes),
                item.metrics?.likes
            )
        }
    }
}