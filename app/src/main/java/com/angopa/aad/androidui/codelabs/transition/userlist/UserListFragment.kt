package com.a

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.angopa.aad.R
import com.angopa.aad.androidui.codelabs.transition.UserData
import com.angopa.aad.androidui.codelabs.transition.userlist.UserListAdapter
import com.angopa.aad.databinding.FragmentTransitionUserListBinding

/**
 *  Created by Andres Gonzalez on 02/27/2020.
 */
class UserListFragment : Fragment(), UserListAdapter.UserListListener {
    private lateinit var binding: FragmentTransitionUserListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentTransitionUserListBinding>(
            inflater,
            R.layout.fragment_transition_user_list,
            container,
            false
        ).apply {
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = UserListAdapter(getUserData(), this@UserListFragment)
            recyclerView.setHasFixedSize(true)
        }

        sharedElementReturnTransition = TransitionInflater
            .from(requireContext())
            .inflateTransition(R.transition.image_shared_element_transition)

        exitTransition = TransitionInflater
            .from(requireContext())
            .inflateTransition(android.R.transition.no_transition)

        return binding.root
    }

    override fun onItemTapped(position: Int) {
        val directions = UserListFragmentDirections.actionDisplayUserDetails()
        this.findNavController().navigate(directions)
    }

    fun getUserData(): Array<UserData> =
        arrayOf(
            UserData(
                "Username",
                "new role"
            ),
            UserData(
                "My User",
                "Unknown"
            )
        )
}