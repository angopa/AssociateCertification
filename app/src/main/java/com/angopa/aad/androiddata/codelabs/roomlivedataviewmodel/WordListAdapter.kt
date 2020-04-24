package com.angopa.aad.androiddata.codelabs.roomlivedataviewmodel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.angopa.aad.R
import com.angopa.aad.androiddata.codelabs.roomlivedataviewmodel.database.Word

/**
 *  Created by Andres Gonzalez on 04/23/2020.
 */
class WordListAdapter : RecyclerView.Adapter<WordListAdapter.ViewHolder>() {

    private var words: List<Word>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val item: View = layoutInflater.inflate(R.layout.list_item_word, parent, false)
        return ViewHolder(item)
    }

    override fun getItemCount(): Int =
        words?.size ?: 0

    fun setWords(words: List<Word>) {
        this.words = words
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (words != null) {
            val word: Word = words!![position]
            holder.wordTextView.text = word.word
        } else {
            holder.wordTextView.text = "No Word"
        }
    }

    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val wordTextView: TextView = item.findViewById(R.id.word_text_view)
    }
}