package com.angopa.aad.androidui.codelabs.motion

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.angopa.aad.R

/**
 *  Created by Andres Gonzalez on 02/10/2020.
 */
class MotionAdapter(private val dataset: Array<Demo>) :
    RecyclerView.Adapter<MotionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val row = LayoutInflater.from(parent.context).inflate(
            R.layout.list_item_motion_row,
            parent,
            false
        ) as ConstraintLayout
        return ViewHolder(row)
    }

    override fun getItemCount(): Int = dataset.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = dataset[position].title
        holder.description.text = dataset[position].description
        holder.layoutFieldId = dataset[position].layout
        holder.activity = dataset[position].activity
    }

    data class Demo(
        val title: String,
        val description: String,
        val layout: Int = 0,
        val activity: Class<*> = MotionDemoActivity::class.java
    ) {
        constructor(
            title: String,
            description: String,
            activity: Class<*> = MotionDemoActivity::class.java
        ) : this(title, description, 0, activity)
    }

    class ViewHolder(val layout: View) : RecyclerView.ViewHolder(layout) {
        var title = layout.findViewById<TextView>(R.id.title_text_view)!!
        var description = layout.findViewById<TextView>(R.id.description_text_view)!!
        var layoutFieldId = 0
        var activity: Class<*>? = null

        init {
            layout.setOnClickListener {
                val context = it?.context as MotionLayoutActivity
                activity?.let {
                    context.start(it, layoutFieldId)
                }
            }
        }

    }
}