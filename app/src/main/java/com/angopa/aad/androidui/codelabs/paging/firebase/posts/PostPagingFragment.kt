package com.angopa.aad.androidui.codelabs.paging.firebase.posts

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.angopa.aad.BaseActivity
import com.angopa.aad.R
import com.angopa.aad.androidui.codelabs.paging.firebase.Error
import com.angopa.aad.androidui.codelabs.paging.firebase.Success
import com.angopa.aad.androidui.codelabs.paging.firebase.SuccessMessage
import com.angopa.aad.androidui.codelabs.paging.firebase.posts.model.Entry
import com.angopa.aad.databinding.FragmentFirebasePagingBinding
import com.angopa.aad.utilities.AppConfiguration
import com.angopa.aad.utilities.DialogFactory
import com.angopa.aad.utilities.InjectorUtils
import com.firebase.ui.database.paging.DatabasePagingOptions
import com.google.firebase.database.FirebaseDatabase

/**
 *  Created by Andres Gonzalez on 03/13/2020.
 */
class PostPagingFragment : Fragment() {
    private lateinit var binding: FragmentFirebasePagingBinding

    private val databaseReference = FirebaseDatabase.getInstance().reference

    private val query = databaseReference.child("entries")

    private lateinit var pagingAdapter: PostAdapter

    private val config = PagedList.Config.Builder()
        .setEnablePlaceholders(true)
        .setPrefetchDistance(5)
        .setPageSize(10)
        .build()

    private val options = DatabasePagingOptions.Builder<Entry>()
        .setLifecycleOwner(this)
        .setQuery(query, config, Entry::class.java)
        .build()

    private val viewModel: FirebaseRealtimeDatabaseViewModel by viewModels {
        InjectorUtils.provideRealtimeViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentFirebasePagingBinding>(
            inflater,
            R.layout.fragment_firebase_paging,
            container,
            false
        ).apply {

            fab.setOnClickListener {
                viewModel.writeNewEntry(Entry())
            }

            swipeRefresh.setOnRefreshListener {
                pagingAdapter.refresh()
            }
            initializeRecyclerView(recyclerView, swipeRefresh)
        }

        setupListeners()
        viewModel.getUser()
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_firebase_paging, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_user -> {
                (activity as BaseActivity).showAlertDialog(
                    DialogFactory(AppConfiguration.get()).createNewUserDialog(
                        requireContext(),
                        LayoutInflater.from(requireContext()),
                        object : DialogFactory.FirebasePagingListener {
                            override fun createNewUser(username: String, email: String) {
                                viewModel.createNewUser(username, email)
                            }
                        }
                    )
                )
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initializeRecyclerView(
        recyclerView: RecyclerView,
        swipeRefresh: SwipeRefreshLayout
    ) {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
        pagingAdapter =
            PostAdapter(
                options
            ) { loading, state ->
                Toast.makeText(requireContext(), state, Toast.LENGTH_SHORT).show()
                swipeRefresh.isRefreshing = loading
            }
        recyclerView.adapter = pagingAdapter
    }

    private fun setupListeners() {
        viewModel.insertState.observe(this, Observer { state ->
            when (state) {
                is Success -> pagingAdapter.refresh()
                is SuccessMessage -> {
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                }
                is Error -> {
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}