package com.angopa.aad.dependencyinjection.daggercodelab.main

import android.content.Intent
import androidx.databinding.DataBindingUtil
import com.angopa.aad.BaseActivity
import com.angopa.aad.CoreApplication
import com.angopa.aad.R
import com.angopa.aad.databinding.ActivityMainDaggerCodelabBinding
import com.angopa.aad.dependencyinjection.daggercodelab.login.LoginActivity
import com.angopa.aad.dependencyinjection.daggercodelab.registration.RegistrationActivity
import com.angopa.aad.dependencyinjection.daggercodelab.settings.SettingsActivity
import javax.inject.Inject

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainDaggerCodelabBinding

    // @Inject annotated fields will be provided by Dagger
    @Inject
    lateinit var mainViewModel: MainViewModel

    override fun getScreenTitle(): Int {
        return R.string.main_screen_dagger_codelab
    }

    /**
     * If the User is not registered, RegistrationActivity will be launched,
     * If the User is not logged in, LoginActivity will be launched,
     * else carry on with MainActivity.
     */
    override fun getBindingComponent() {
        // Grabs instance of UserManager from the application graph
        val userManager = (application as CoreApplication).appComponentCodelab.userManager()
        if (!userManager.isUserLoggedIn()) {
            if (!userManager.isUserRegistered()) {
                startActivity(Intent(this, RegistrationActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        } else {
            binding = DataBindingUtil.setContentView(this, R.layout.activity_main_dagger_codelab)
            // If the MainActivity needs to be displayed, we get the UserComponent from the
            // application graph and gets this Activity injected
            userManager.userComponent!!.inject(this)
            setupView()
        }
    }

    private fun setupView() {
        binding.hello.text = mainViewModel.welcomeText
        binding.logout.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }

    /**
     * Updating unread notifications onResume because they can get updated on SettingsActivity
     */
    override fun onResume() {
        super.onResume()
        binding.notifications.text = mainViewModel.notificationsText
    }
}