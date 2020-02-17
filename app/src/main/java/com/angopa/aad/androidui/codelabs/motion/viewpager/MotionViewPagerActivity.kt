package com.angopa.aad.androidui.codelabs.motion.viewpager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.viewpager.widget.ViewPager
import com.angopa.aad.R
import kotlinx.android.synthetic.main.fragment_motion_16_viewpager.*

class MotionViewPagerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_motion_16_viewpager)

        val motionLayout = findViewById<MotionLayout>(R.id.motionLayout)

        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addPage("Page 1", R.layout.fragment_motion_16_viewpager_page1)
        adapter.addPage("Page 2", R.layout.fragment_motion_16_viewpager_page2)
        adapter.addPage("Page 3", R.layout.fragment_motion_16_viewpager_page3)
        pager.adapter = adapter
        tabs.setupWithViewPager(pager)
        if (motionLayout != null) {
            pager.addOnPageChangeListener(motionLayout as ViewPager.OnPageChangeListener)
        }
    }

}
