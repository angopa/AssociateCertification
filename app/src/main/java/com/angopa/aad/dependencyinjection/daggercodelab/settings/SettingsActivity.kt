package com.angopa.aad.dependencyinjection.daggercodelab.settings

import android.content.Intent
import androidx.databinding.DataBindingUtil
import com.angopa.aad.BaseActivity
import com.angopa.aad.CoreApplication
import com.angopa.aad.R
import com.angopa.aad.databinding.ActivitySettingsDaggerCodelabBinding
import com.angopa.aad.dependencyinjection.daggercodelab.login.LoginActivity
import javax.inject.Inject

class SettingsActivity : BaseActivity() {
    private lateinit var binding: ActivitySettingsDaggerCodelabBinding

    @Inject
    lateinit var settingsViewModel: SettingsViewModel

    override fun getScreenTitle(): Int {
        return R.string.settings_screen_title
    }

    override fun getBindingComponent() {
        // Gets the userManager from the application graph to obtain the UserComponent
        // and gets this Activity injected
        val userManager = (application as CoreApplication).appComponentCodelab.userManager()
        userManager.userComponent!!.inject(this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings_dagger_codelab)

        setupViews()
    }

    private fun setupViews() {
        binding.refresh.setOnClickListener {
            settingsViewModel.refreshNotifications()
        }

        binding.logout.setOnClickListener {
            settingsViewModel.logout()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }

}