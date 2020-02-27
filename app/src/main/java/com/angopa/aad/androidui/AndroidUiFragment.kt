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
import com.angopa.aad.androidui.codelabs.adaptiveui.NewsReaderActivity
import com.angopa.aad.androidui.codelabs.constraintlayout.ConstraintLayoutActivity
import com.angopa.aad.androidui.codelabs.motion.MotionLayoutActivity
import com.angopa.aad.androidui.codelabs.navigation.NavigationActivity
import com.angopa.aad.androidui.codelabs.paging.PagingDashboardActivity
import com.angopa.aad.androidui.codelabs.recyclerview.RecyclerViewActivity
import com.angopa.aad.androidui.codelabs.transition.TransitionActivity
import com.angopa.aad.androidui.codelabs.useravatar.UserAvatarFragment
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
                override fun userImageExample() =
                    launchActivity(UserAvatarFragment::class.java)

                override fun constraintLayout() =
                    launchActivity(ConstraintLayoutActivity::class.java)

                override fun motionLayout() =
                    launchActivity(MotionLayoutActivity::class.java)

                override fun recyclerView() =
                    launchActivity(RecyclerViewActivity::class.java)

                override fun paging() {
                    launchActivity(PagingDashboardActivity::class.java)
                }

                override fun adaptiveUi() {
                    launchActivity(NewsReaderActivity::class.java)
                }

                override fun navigationDrawer() {
                    launchActivity(NavigationActivity::class.java)
                }

                override fun transition() {
                    launchActivity(TransitionActivity::class.java)
                }
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
        fun constraintLayout()
        fun motionLayout()
        fun recyclerView()
        fun adaptiveUi()
        fun paging()
        fun navigationDrawer()
        fun transition()
    }
}