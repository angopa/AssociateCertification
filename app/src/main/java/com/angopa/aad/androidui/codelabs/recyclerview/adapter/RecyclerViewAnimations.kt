package com.angopa.aad.androidui.codelabs.recyclerview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.angopa.aad.R
import com.angopa.aad.utilities.Utils.Companion.generateColor

/**
 *  Created by Andres Gonzalez on 02/04/2020.
 *
 * Custom adapter that supplies view holders to the RecyclerView. Our view holders
 * contain a simple LinearLayout (with a background color) and a TextView (displaying
 * the value of the container's bg color).
 */
class RecyclerViewAnimations(
    private val recyclerView: RecyclerView,
    private val colors: ArrayList<Int>,
    private val radioGroup: RadioGroup
) : RecyclerView.Adapter<RecyclerViewAnimations.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_recycler_view_animation, parent, false).apply {
                    setOnClickListener {
                        when (radioGroup.checkedRadioButtonId) {
                            R.id.delete_rb -> deleteItem(it)
                            R.id.add_rb -> addItem(it)
                            R.id.change_rb -> changeItem(it)
                        }
                    }
                }
        )
    }

    private fun deleteItem(view: View) {
        val position = recyclerView.getChildAdapterPosition(view)
        if (position != RecyclerView.NO_POSITION) {
            colors.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    private fun addItem(view: View) {
        val position = recyclerView.getChildAdapterPosition(view)
        if (position != RecyclerView.NO_POSITION) {
            colors.add(generateColor())
            notifyItemInserted(position)
        }
    }

    private fun changeItem(view: View) {
        val position = recyclerView.getChildAdapterPosition(view)
        if (position != RecyclerView.NO_POSITION) {
            colors[position] = generateColor()
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int = colors.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val color = colors[position]
        holder.textView.text = "#${Integer.toHexString(color)}"
        holder.view.setBackgroundColor(color)
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.text_view)

        override fun toString(): String = "${super.toString()} \" ${textView.text}\""

    }
}