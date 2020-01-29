package com.angopa.aad.dependencyinjection.daggercodelab.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.angopa.aad.BaseActivity
import com.angopa.aad.CoreApplication
import com.angopa.aad.R
import com.angopa.aad.databinding.ActivityLoginDaggerCodelabBinding
import com.angopa.aad.databinding.ActivityMainDaggerCodelabBinding
import com.angopa.aad.dependencyinjection.daggercodelab.BaseActivityDagger
import com.angopa.aad.dependencyinjection.daggercodelab.main.MainActivity
import com.angopa.aad.dependencyinjection.daggercodelab.registration.RegistrationActivity
import timber.log.Timber
import javax.inject.Inject

class LoginActivity : BaseActivityDagger() {
    private lateinit var binding: ActivityLoginDaggerCodelabBinding

    // @Inject annotated fields will be provided by Dagger
    @Inject
    lateinit var loginViewModel: LoginViewModel

    private lateinit var errorTextView: TextView

    override fun getComponent() {
        (application as CoreApplication).appComponentCodelab.loginComponent().create().inject(this)
    }

    override fun getScreenTitle(): Int {
        return R.string.main_screen_dagger_codelab
    }

    override fun getBindingComponent() {
        binding = DataBindingUtil.setContentView<ActivityLoginDaggerCodelabBinding>(
            this,
            R.layout.activity_login_dagger_codelab
        ).apply {
            //Setup Views
            errorTextView = error

            username.isEnabled = false
            username.setText(loginViewModel.getUserName())

            password.doOnTextChanged { _, _, _, _ -> errorTextView.visibility = View.INVISIBLE }

            login.setOnClickListener {
                loginViewModel.login(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            unregister.setOnClickListener {
                loginViewModel.unregister()
                startRegistrationActivity()
            }

        }

        loginViewModel.loginState.observe(this, Observer<LoginViewState> { state ->
            when (state) {
                is LoginSuccess -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                is LoginError -> errorTextView.visibility = View.VISIBLE
            }
        })
    }

    private fun startRegistrationActivity() {
        val intent = Intent(this@LoginActivity, RegistrationActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                Intent.FLAG_ACTIVITY_CLEAR_TASK or
                Intent.FLAG_ACTIVITY_NEW_TASK

        startActivity(intent)
    }
}

sealed class LoginViewState
object LoginSuccess : LoginViewState()
object LoginError : LoginViewState()