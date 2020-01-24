package com.angopa.aad.androidui.codelabs.useravatar

import android.os.Handler
import androidx.databinding.DataBindingUtil
import com.angopa.aad.BaseActivity
import com.angopa.aad.R
import com.angopa.aad.databinding.ActivityUserAvatarBinding
import timber.log.Timber

class UserAvatarFragment : BaseActivity() {
    private lateinit var dataBinding: ActivityUserAvatarBinding
    private lateinit var users: ArrayList<User>

    override fun getScreenTitle() = R.string.ui_codelab_avatar_screen_title

    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    override fun getBindingComponent() {
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_avatar)

        users = UserDummyList.createDummyUserList()

        handler = Handler()

        runnable = Runnable {
            val index = (0..4).random()
            Timber.d("Running... user $index")
            dataBinding.userAvatar.setUp(users[index])
            handler.postDelayed(runnable, 4000)
        }

        handler.postDelayed(runnable, 1000)
    }
}