package com.angopa.aad.dependencyinjection.daggerbasic

import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import com.angopa.aad.CoreApplication
import com.angopa.aad.R
import com.angopa.aad.databinding.ActivityLoginDaggerBinding
import timber.log.Timber
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginDaggerBinding

    private var userData: LoginUserData = LoginUserData()

    //Fields that need to be injected by the login graph
    @Inject
    lateinit var loginViewModel: LoginViewModel

    //Reference to the Login graph
    lateinit var loginComponent: LoginComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        // Creation of the login graph using the application graph
        loginComponent = (application as CoreApplication).appComponent.loginComponent().create()

        // Make Dagger instantiate @Inject fields in LoginActivity
        loginComponent.inject(this)

        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView<ActivityLoginDaggerBinding>(
            this@LoginActivity,
            R.layout.activity_login_dagger
        ).apply {
            callback = object : Callback {
                override fun loadData() = loadUserInfo()
            }
            userDataVariable = this@LoginActivity.userData
        }
    }

    private fun loadUserInfo() {
        loginViewModel.loadData().observe(this@LoginActivity) { currentName ->
            if (!TextUtils.isEmpty(currentName)) {
                Timber.d("User Name: $currentName")
                userData.setUserName(currentName)
            }
        }
    }

    interface Callback {
        fun loadData()
    }
}