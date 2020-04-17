package com.angopa.aad.androidui.codelabs.transition.activitytoactivity

import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.databinding.DataBindingUtil
import com.angopa.aad.BaseActivity
import com.angopa.aad.R
import com.angopa.aad.androidui.codelabs.transition.UserData
import com.angopa.aad.androidui.codelabs.transition.fragmenttofragment.userlist.UserListAdapter
import com.angopa.aad.databinding.ActivityTransitionStartBinding

/**
 *  Created by Andres Gonzalez on 03/28/2020.
 */
class TransitionStartActivity : BaseActivity(), UserListAdapter.UserListListener {
    private lateinit var binding: ActivityTransitionStartBinding

    override fun getScreenTitle(): Int = R.string.screen_title_transition

    override fun getBindingComponent() {
        binding = DataBindingUtil.setContentView<ActivityTransitionStartBinding>(
            this,
            R.layout.activity_transition_start
        ).apply {
            recyclerView.adapter = UserListAdapter(getUserData(), this@TransitionStartActivity)
            recyclerView.setHasFixedSize(true)
        }
    }

    fun getUserData(): Array<UserData> =
        arrayOf(
            UserData(
                "Username",
                "new role"
            ),
            UserData(
                "Username",
                "Unknown"
            ),
            UserData(
                "Username",
                "Unknown"
            ),
            UserData(
                "Username",
                "Unknown"
            ),
            UserData(
                "Username",
                "Unknown"
            ),
            UserData(
                "Username",
                "Unknown"
            ),
            UserData(
                "Username",
                "Unknown"
            ),
            UserData(
                "Username",
                "Unknown"
            )
        )

    override fun onItemTapped(
        adapterPosition: Int,
        profileImage: ImageView,
        username: TextView
    ) {
        val intent = Intent(
            this@TransitionStartActivity,
            TransitionEndActivity::class.java
        )
        profileImage.transitionName = VIEW_NAME_HEADER_IMAGE
        username.transitionName = VIEW_NAME_HEADER_TEXT
        // create the transition animation - the images in the layouts
        // of both activities are defined with android:transitionName="profile_image"
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this@TransitionStartActivity,
            Pair.create<View, String>(profileImage, VIEW_NAME_HEADER_IMAGE),
            Pair.create<View, String>(username, VIEW_NAME_HEADER_TEXT)
        )
        // start the new activity providing the activity options as a bundle
        ActivityCompat.startActivity(this@TransitionStartActivity, intent, options.toBundle())
    }
}