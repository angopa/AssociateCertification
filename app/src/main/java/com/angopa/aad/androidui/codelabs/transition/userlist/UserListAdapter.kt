package com.angopa.aad.androidui.codelabs.transition.userlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.angopa.aad.R
import com.angopa.aad.androidui.codelabs.transition.UserData

/**
 *  Created by Andres Gonzalez on 02/27/2020.
 */
class UserListAdapter(
    private val dataset: Array<UserData>,
    private val listener: UserListListener
) : RecyclerView.Adapter<UserListAdapter.UserListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_transition, parent, false)
        return UserListViewHolder(listener, view)
    }

    override fun getItemCount(): Int = dataset.size

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        holder.username.text = dataset[position].username
        holder.userRole.text = dataset[position].role
        // the view being shared
        holder.profileImage.transitionName = "transition $position"
    }

    class UserListViewHolder(
        private val listener: UserListListener,
        private val item: View
    ) : RecyclerView.ViewHolder(item) {
        val profileImage: ImageView = item.findViewById(R.id.profile_image)
        val username: TextView = item.findViewById(R.id.username)
        val userRole: TextView = item.findViewById(R.id.user_role)

        init {

            item.setOnClickListener {
                listener.onItemTapped(adapterPosition)
            }
        }
    }

    interface UserListListener {
        fun onItemTapped(adapterPosition: Int)
    }
}