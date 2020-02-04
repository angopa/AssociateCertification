package com.angopa.aad.androidui.codelabs.recyclerview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.angopa.aad.R

/**
 *  Created by Andres Gonzalez on 02/03/2020.
 */
class SimpleRecyclerAdapter(
    private val dataset: Array<String>
) : RecyclerView.Adapter<SimpleRecyclerAdapter.ViewHolder>() {

    // Create new views (invoke by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_simple_recycler_view, parent, false)

        return ViewHolder(view)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int = dataset.size

    // Replace the content of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the content of the view with that element
        holder.getTextView().text = dataset[position]
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is juts a string in this case that is show in a TextView
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private var textView: TextView = view.findViewById(R.id.text_view)

        fun getTextView() : TextView = textView
    }
}