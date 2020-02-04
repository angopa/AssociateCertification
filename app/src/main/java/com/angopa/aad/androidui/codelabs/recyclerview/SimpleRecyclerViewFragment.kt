package com.angopa.aad.androidui.codelabs.recyclerview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.angopa.aad.R
import com.angopa.aad.androidui.codelabs.recyclerview.adapter.SimpleRecyclerAdapter
import com.angopa.aad.databinding.FragmentSimpleRecyclerViewBinding

private const val KEY_LAYOUT_MANAGER = "layoutManager"
private const val SPAN_COUNT = 2
private const val DATASET_COUNT = 120

/**
 *  Created by Andres Gonzalez on 02/03/2020.
 *
 *  Demonstrates the use of {@link RecyclerView} with a {@link LinearLayoutManager} and a
 *  {@link GirdLayoutManager}
 */
class SimpleRecyclerViewFragment : Fragment() {
    private lateinit var binding: FragmentSimpleRecyclerViewBinding

    private lateinit var currentLayoutManagerType: LayoutManagerType
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var dataset: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize dataset, this data would usually come from a local content provider or
        // remote server.
        initDataset()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentSimpleRecyclerViewBinding>(
            inflater,
            R.layout.fragment_simple_recycler_view,
            container,
            false
        ).apply {
            linearLayoutRb.setOnClickListener {
                setRecyclerViewLayoutManager(LayoutManagerType.LINEAR_LAYOUT_MANAGER)
            }

            gridLayoutRb.setOnClickListener {
                setRecyclerViewLayoutManager(LayoutManagerType.GRID_LAYOUT_MANAGER)
            }
        }

        // Linear layout manager is used here, this will layout the elements in a similar fashion
        // to the way ListView would layout elements. The RecyclerView.LayoutManager defines how
        // elements are laid out.
        layoutManager = LinearLayoutManager(requireContext())
        currentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER

        if (savedInstanceState != null) {
            //Restore saved layout manager type
            currentLayoutManagerType =
                savedInstanceState.getSerializable(KEY_LAYOUT_MANAGER) as LayoutManagerType
        }

        setRecyclerViewLayoutManager(currentLayoutManagerType)

        //Set SimpleRecyclerAdapter as the adapter for RecyclerView
        binding.recyclerView.adapter = SimpleRecyclerAdapter(dataset)

        return binding.root
    }

    private fun setRecyclerViewLayoutManager(layoutManagerType: LayoutManagerType) {
        var scrollPosition = 0

        // If a layout manager has already been set, get current scroll position
        if (binding.recyclerView.layoutManager != null) {
            scrollPosition =
                (binding.recyclerView.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
        }

        when (layoutManagerType) {
            LayoutManagerType.LINEAR_LAYOUT_MANAGER -> {
                layoutManager = LinearLayoutManager(requireContext())
                currentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER
            }
            LayoutManagerType.GRID_LAYOUT_MANAGER -> {
                layoutManager = GridLayoutManager(requireContext(), SPAN_COUNT)
                currentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER
            }
        }

        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.scrollToPosition(scrollPosition)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        // Save current selected layout manager
        outState.putSerializable(KEY_LAYOUT_MANAGER, currentLayoutManagerType)
        super.onSaveInstanceState(outState)
    }

    enum class LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    private fun initDataset() {
        dataset = Array(DATASET_COUNT) { "This is element $it" }
    }
}