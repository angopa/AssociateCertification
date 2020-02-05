package com.angopa.aad

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.angopa.aad.androidcore.AndroidCoreFragment
import com.angopa.aad.androiddata.AndroidDataManagementFragment
import com.angopa.aad.androidui.AndroidUiFragment
import com.angopa.aad.dependencyinjection.DaggerFragment
import com.angopa.aad.map.MapFragment
import java.lang.IndexOutOfBoundsException



const val ANDROID_CORE_PAGE_INDEX = 0
const val USER_INTERFACE_PAGE_INDEX = 1
const val DATA_MANAGEMENT_PAGE_INDEX = 2
const val DEBUGGING_PAGE_INDEX = 3
const val TESTING_PAGE_INDEX = 4
const val DAGGER_PAGE_INDEX = 5
const val MAP_PAGE_INDEX = 6

class MainPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    /**
     * Mapping of the ViewPager page indexes to their respective Fragments
     */
    private val tabFragmentCreator: Map<Int, () -> Fragment> = mapOf(
        ANDROID_CORE_PAGE_INDEX to { AndroidCoreFragment() },
        USER_INTERFACE_PAGE_INDEX to { AndroidUiFragment() },
        DATA_MANAGEMENT_PAGE_INDEX to { AndroidDataManagementFragment() },
        DEBUGGING_PAGE_INDEX to { AndroidDebugging() },
        TESTING_PAGE_INDEX to { AndroidTestFragment() },
        DAGGER_PAGE_INDEX to { DaggerFragment() },
        MAP_PAGE_INDEX to { MapFragment() }
    )

    override fun getItemCount(): Int = tabFragmentCreator.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentCreator[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }

}