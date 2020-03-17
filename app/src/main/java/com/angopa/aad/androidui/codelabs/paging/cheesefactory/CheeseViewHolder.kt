package com.angopa.aad.androidui.codelabs.paging.cheesefactory

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.angopa.aad.R
import com.angopa.aad.data.localdata.model.Cheese

/**
 *  Created by Andres Gonzalez on 02/18/2020.
 *
 * A simple ViewHolder that can bind a Cheese item. It also accepts null items since the data may
 * not have been fetched before it is bound.
 *
 */
class CheeseViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.list_item_cheese, parent, false)
) {
    private val nameView = itemView.findViewById<TextView>(R.id.name)
    var cheese: Cheese? = null

    /**
     * Items might be null if they are not paged in yet. PagedListAdapter will re-bind the
     * ViewHolder when Item is loaded.
     */
    fun bindTo(cheese: Cheese?) {
        this.cheese = cheese
        nameView.text = cheese?.name
    }
}