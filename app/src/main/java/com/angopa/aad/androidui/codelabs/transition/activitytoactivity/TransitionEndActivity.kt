package com.angopa.aad.androidui.codelabs.transition.activitytoactivity

import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import com.angopa.aad.BaseActivity
import com.angopa.aad.R
import com.angopa.aad.databinding.ActivityTransitionEndBinding

// View name of the header image. Used for activity scene transitions
const val VIEW_NAME_HEADER_IMAGE = "detail:header:image"

// View name of the header image. Used for activity scene transitions
const val VIEW_NAME_HEADER_TEXT = "detail:header:text"

/**
 *  Created by Andres Gonzalez on 03/28/2020.
 */
class TransitionEndActivity : BaseActivity() {
    private lateinit var binding: ActivityTransitionEndBinding

    override fun getScreenTitle(): Int = R.string.screen_title_transition

    override fun getBindingComponent() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_transition_end)
        /*
         * Set the name of the view's which will be transition to, using the static values above.
         * This could be done in the layout XML, but exposing it via static variables allows easy
         * querying from other Activities
         */
        ViewCompat.setTransitionName(binding.profileImage, VIEW_NAME_HEADER_IMAGE)
        ViewCompat.setTransitionName(binding.userName, VIEW_NAME_HEADER_TEXT)
    }

    override fun onBackPressed() {
        finishAfterTransition()
    }

}