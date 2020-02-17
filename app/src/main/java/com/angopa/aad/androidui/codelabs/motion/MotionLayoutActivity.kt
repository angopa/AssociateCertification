package com.angopa.aad.androidui.codelabs.motion

import android.content.Intent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.angopa.aad.BaseActivity
import com.angopa.aad.R
import com.angopa.aad.androidui.codelabs.motion.viewpager.MotionViewPagerActivity
import com.angopa.aad.databinding.ActivityMotionLayoutBinding

/**
 *  Created by Andres Gonzalez on 02/03/2020.
 */
class MotionLayoutActivity : BaseActivity() {
    private lateinit var binding: ActivityMotionLayoutBinding

    override fun getScreenTitle(): Int = R.string.label_title_motion_layout

    override fun getBindingComponent() {
        binding = DataBindingUtil.setContentView<ActivityMotionLayoutBinding>(
            this,
            R.layout.activity_motion_layout
        ).apply {
            val layoutManager = LinearLayoutManager(this@MotionLayoutActivity)
            val adapter = MotionAdapter(dataset)
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = adapter
            recyclerView.setHasFixedSize(true)
        }
    }

    fun start(activity: Class<*>, layoutFileId: Int) {
        if (layoutFileId == 0) {
            startActivity(Intent(this, activity))
        } else {
            startActivity(MotionDemoActivity.getInstance(this, layoutFileId))
        }
    }

    private val dataset: Array<MotionAdapter.Demo> = arrayOf(
//        MotionAdapter.Demo(
//            "Basic Example (1/2)",
//            "Basic motion example using ConstraintSets defined in the MotionScene file",
//            R.layout.fragment_motion_01_basic
//        ),
//        MotionAdapter.Demo(
//            "Basic Example (2/2)",
//            "Basic motion example same as 2, but autoComplete is set to false in onSwipe",
//            R.layout.fragment_motion_02_basic
//        ),
//        MotionAdapter.Demo(
//            "Custom Attribute",
//            "Show color interpolation (custom attribute)",
//            R.layout.fragment_motion_03_custom_attribute
//        ),
//        MotionAdapter.Demo(
//            "ImageFilterView (1/2)",
//            "Show image cross-fade (using ML's ImageFilterView + custom attribute)",
//            R.layout.fragment_motion_04_imagefilter
//        ),
//        MotionAdapter.Demo(
//            "ImageFilterView (2/2)",
//            "Show image saturation transition (using ML's ImageFilterView + custom attribute)",
//            R.layout.fragment_motion_05_imagefilter
//        ),
//        MotionAdapter.Demo(
//            "Keyframe Position (1 / 3)",
//            " Use a simple keyframe to change the interpolated motion ",
//            R.layout.fragment_motion_06_keyframe
//        ),
//        MotionAdapter.Demo(
//            "Keyframe Interpolation (2/3)",
//            "More complex keyframe, adding rotation interpolation",
//            R.layout.fragment_motion_07_keyframe
//        ),
//        MotionAdapter.Demo(
//            "Keyframe Cycle (3/3)",
//            "Basic example of using a keyframe cycle ",
//            R.layout.fragment_motion_08_cycle
//        ),
//        MotionAdapter.Demo(
//            "CoordinatorLayout Example (1/3)",
//            "Basic example of using MotionLayout instead of AppBarLayout",
//            R.layout.fragment_motion_09_coordinatorlayout
//        ),
//        MotionAdapter.Demo(
//            "CoordinatorLayout Example (2/3)",
//            "Slightly more complex example of MotionLayout replacing AppBarLayout, with multiple elements and parallax background",
//            R.layout.fragment_motion_10_coordinatorlayout
//        ),
//        MotionAdapter.Demo(
//            "CoordinatorLayout Example (3/3)",
//            "Another AppBarLayout replacement example",
//            R.layout.fragment_motion_11_coordinatorlayout
//        ),
//        MotionAdapter.Demo(
//            "DrawerLayout Example (1/2)",
//            "Basic DrawerLayout with MotionLayout",
//            R.layout.fragment_motion_12_drawerlayout
//        ),
//        MotionAdapter.Demo(
//            "DrawerLayout Example (2/2)",
//            "Advanced DrawerLayout with MotionLayout",
//            R.layout.fragment_motion_13_drawerlayout
//        ),
//        MotionAdapter.Demo(
//            "Side Panel Example",
//            "Side Panel, implemented with MotionLayout only",
//            R.layout.fragment_motion_14_side_panel
//        ),
        MotionAdapter.Demo(
            "Parallax Example",
            "Parallax background. Drag the car.",
            R.layout.fragment_motion_15_parallax
        ),
        MotionAdapter.Demo(
            "ViewPager Example",
            "Using MotionLayout with ViewPager",
            MotionViewPagerActivity::class.java
        )
    )
}