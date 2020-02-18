package com.angopa.aad.androidui.codelabs.paging

import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.angopa.aad.Application
import com.angopa.aad.BaseActivity
import com.angopa.aad.R
import com.angopa.aad.androidui.codelabs.paging.adapter.CheeseAdapter
import com.angopa.aad.androidui.codelabs.paging.viewholder.CheeseViewHolder
import com.angopa.aad.androidui.codelabs.paging.viewmodel.CheeseViewModel
import com.angopa.aad.databinding.ActivityCheeseFactoryPagingBinding
import com.angopa.aad.utilities.InjectorUtils

/**
 *  Created by Andres Gonzalez on 02/18/2020.
 *
 * Shows a list of Cheeses, with swipe-to-delete, and an input field at the top to add.
 * <p>
 * Cheeses are stored in a database, so swipes and additions edit the database directly, and the UI
 * is updated automatically using paging components.
 */
class CheeseFactoryPagingActivity : BaseActivity() {
    private lateinit var binding: ActivityCheeseFactoryPagingBinding

    private val viewModel: CheeseViewModel by viewModels {
        InjectorUtils.provideCheeseViewModelFactory(this@CheeseFactoryPagingActivity, Application())
    }

    override fun getScreenTitle(): Int = R.string.screen_title_cheese_factory

    override fun getBindingComponent() {
        binding = DataBindingUtil.setContentView<ActivityCheeseFactoryPagingBinding>(
            this,
            R.layout.activity_cheese_factory_paging
        ).apply {
            // Create adapter for the RecyclerView
            val adapter = CheeseAdapter()
            cheeseList.adapter = adapter

            // Subscribe the adapter to the ViewModel, so the items in the adapter are refreshed
            // when the list changes
            viewModel.allCheeses.observe(
                this@CheeseFactoryPagingActivity,
                Observer(adapter::submitList)
            )
        }

        initAddButtonListener()
        initSwipeToDelete()
    }

    private fun initSwipeToDelete() {
        ItemTouchHelper(object : ItemTouchHelper.Callback() {
            // enable the items to swipe to the left or right
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int = makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = true

            // When an item is swiped, remove the item via the view model. The list item will be
            // automatically removed in response, because the adapter is observing the live list.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                (viewHolder as CheeseViewHolder).cheese?.let {
                    viewModel.remove(it)
                }
            }
        }).attachToRecyclerView(binding.cheeseList)
    }

    private fun initAddButtonListener() {
        binding.addButton.setOnClickListener {
            addCheese()
        }

        // when the user taps the "Done" button in the on screen keyboard, save the item.
        binding.inputText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                addCheese()
                return@setOnEditorActionListener true
            }
            false // action that isn't DONE occurred - ignore
        }

        // When the user clicks on the button, or presses enter, save the item.
        binding.inputText.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                addCheese()
                return@setOnKeyListener true
            }
            false // event that isn't DOWN or ENTER occurred - ignore
        }
    }

    private fun addCheese() {
        val newCheese = binding.inputText.text.trim()
        if (newCheese.isNotEmpty()) {
            viewModel.insert(newCheese)
            binding.inputText.setText("")
        }
    }
}