package com.angopa.aad.androidui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.angopa.aad.R
import com.angopa.aad.adapters.LinkAdapter
import com.angopa.aad.androidui.codelabs.useravatar.UserAvatarContainer
import com.angopa.aad.databinding.FragmentAndroidUiBinding
import com.angopa.aad.utilities.InjectorUtils

class AndroidUiFragment : Fragment() {

    private lateinit var binding: FragmentAndroidUiBinding

    private val androidUiViewModel: AndroidUiViewModel by viewModels {
        InjectorUtils.provideAndroidUiViewModelFactory(requireContext(), "android_ui")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentAndroidUiBinding>(
            inflater,
            R.layout.fragment_android_ui, container, false
        ).apply {
            viewModel = androidUiViewModel
            lifecycleOwner = viewLifecycleOwner

            val adapter = LinkAdapter()
            linksRecyclerView.adapter = adapter

            subscribeUi(adapter)

            callback = object : Callback {
                override fun userImageExample() = launchActivity(UserAvatarContainer::class.java)
            }
        }

        return binding.root
    }

    private fun <T : Activity> launchActivity(activity: Class<T>) {
        startActivity(Intent(requireContext(), activity))
    }

    private fun subscribeUi(adapter: LinkAdapter) {
        androidUiViewModel.links.observe(viewLifecycleOwner) { result ->
            adapter.submitList(result)
        }
    }

    interface Callback {
        fun userImageExample()
    }

}