package com.angopa.aad

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.angopa.aad.databinding.FragmentViewPagerBinding
import com.google.android.material.tabs.TabLayoutMediator
import java.lang.IndexOutOfBoundsException


class HomeViewPagerFragment : Fragment() {
    private lateinit var binding: FragmentViewPagerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentViewPagerBinding>(
            inflater,
            R.layout.fragment_view_pager,
            container,
            false
        ).apply {
            viewPager.adapter = MainPagerAdapter(this@HomeViewPagerFragment)
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.setIcon(getTabIcon(position))
                tab.text = getTabText(position)
            }.attach()
        }

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        return binding.root
    }

    private fun getTabIcon(position: Int): Int {
        return when (position) {
            ANDROID_CORE_PAGE_INDEX -> R.drawable.ic_android_core
            USER_INTERFACE_PAGE_INDEX -> R.drawable.ic_user_interface
            DATA_MANAGEMENT_PAGE_INDEX -> R.drawable.ic_data_management
            DEBUGGING_PAGE_INDEX -> R.drawable.ic_debugging
            TESTING_PAGE_INDEX -> R.drawable.ic_testing
            DAGGER_PAGE_INDEX -> R.drawable.ic_dagger
            else -> throw IndexOutOfBoundsException()
        }
    }

    private fun getTabText(position: Int): String {
        return when (position) {
            ANDROID_CORE_PAGE_INDEX -> getString(R.string.tab_title_android_core)
            USER_INTERFACE_PAGE_INDEX -> getString(R.string.tab_title_user_interface)
            DATA_MANAGEMENT_PAGE_INDEX -> getString(R.string.tab_title_data_management)
            DEBUGGING_PAGE_INDEX -> getString(R.string.tab_title_debugging)
            TESTING_PAGE_INDEX -> getString(R.string.tab_title_testing)
            DAGGER_PAGE_INDEX -> getString(R.string.tab_title_dagger)
            else -> throw IndexOutOfBoundsException()
        }
    }
}