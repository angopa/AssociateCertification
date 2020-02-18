package com.angopa.aad.androidui.codelabs.paging.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.angopa.aad.R
import com.angopa.aad.data.localdata.Post
import com.angopa.aad.utilities.Utils
import com.bumptech.glide.Glide

/**
 *  Created by Andres Gonzalez on 02/05/2020.
 */
class PostAdapter(
    private val context: Context,
    private val listener: PostActionsListener,
    private val dataset: ArrayList<Post>
) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_post, parent, false)
        return ViewHolder(
            view
        )
    }

    override fun getItemCount(): Int = dataset.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post: Post = dataset[position]
        holder.userName.text = post.userName
        holder.date.text = Utils.formatDateFromMillis(post.date)
        holder.comment.text = post.caption

        Glide.with(context)
            .asBitmap()
            .error(R.drawable.lake)
            .load(post.imageUrl)
            .placeholder(R.drawable.progress_animation)
            .into(holder.postImage)

        holder.setting.setOnClickListener {
            listener.settingAction(post.postId)
        }
    }

    fun updateData(newData: List<Post>) {
        dataset.addAll(newData)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val userName: TextView = view.findViewById(R.id.user_name_text_view)
        val date: TextView = view.findViewById(R.id.date_text_view)
        val postImage: ImageView = view.findViewById(R.id.post_image_view)
        val comment: TextView = view.findViewById(R.id.caption_text_view)
        val setting: ImageView = view.findViewById(R.id.setting_image_view)
    }

    interface PostActionsListener {
        fun settingAction(postId: String)
    }
}