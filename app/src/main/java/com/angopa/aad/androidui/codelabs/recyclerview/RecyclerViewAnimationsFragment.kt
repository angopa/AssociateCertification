package com.angopa.aad.androidui.codelabs.recyclerview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import com.angopa.aad.R
import com.angopa.aad.androidui.codelabs.recyclerview.adapter.RecyclerViewAnimations
import com.angopa.aad.androidui.codelabs.recyclerview.animator.MyChangeAnimator
import com.angopa.aad.androidui.codelabs.recyclerview.layoutmanager.MyLinearLayoutManager
import com.angopa.aad.databinding.FragmentRecyclerViewAnimationsBinding
import com.angopa.aad.utilities.Utils

private const val MAX_NUMBER_OF_ROWS = 5

/**
 *  Created by Andres Gonzalez on 02/03/2020.
 */
class RecyclerViewAnimationsFragment : Fragment() {
    private lateinit var databing: FragmentRecyclerViewAnimationsBinding
    private val colors = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        generateData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        databing = DataBindingUtil.inflate<FragmentRecyclerViewAnimationsBinding>(
            inflater,
            R.layout.fragment_recycler_view_animations,
            container,
            false
        ).apply {
            recyclerView.layoutManager = MyLinearLayoutManager(requireContext(), predictiveCb)
            recyclerView.adapter = RecyclerViewAnimations(recyclerView, colors, radioGroup)
            customCb.setOnCheckedChangeListener { _, isChecked ->
                recyclerView.itemAnimator =
                    if (isChecked) MyChangeAnimator() else DefaultItemAnimator()
            }
        }

        return databing.root
    }

    private fun generateData() {
        for (i in 0..MAX_NUMBER_OF_ROWS) {
            colors.add(Utils.generateColor())
        }
    }
}