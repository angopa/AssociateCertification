package com.angopa.aad.androiddata.codelabs.roomlivedataviewmodel

import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.angopa.aad.BaseActivity
import com.angopa.aad.R
import com.angopa.aad.databinding.ActivityRoomLivedataViewmodelBinding

/**
 *  Created by Andres Gonzalez on 04/21/2020.
 */
class RoomCodelabActivity : BaseActivity() {
    private lateinit var binding: ActivityRoomLivedataViewmodelBinding

    private lateinit var wordViewModel: WordViewModel

    override fun getScreenTitle(): Int {
        return R.string.room_livedata_viewmodel_screen_title
    }

    override fun getBindingComponent() {
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_room_livedata_viewmodel
        )
        wordViewModel = ViewModelProviders.of(this).get(WordViewModel::class.java)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val adapter = WordListAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        wordViewModel?.allWords?.observe(this, Observer {
            adapter.setWords(it)
        })

    }
}