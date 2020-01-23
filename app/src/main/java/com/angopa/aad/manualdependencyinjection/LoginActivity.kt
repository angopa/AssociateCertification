package com.angopa.aad.manualdependencyinjection

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import com.angopa.aad.CoreApplication

/**
 * Manual Dependency Injection, creating the required dependencies
 * where we use the loginViewModel class.
 */
@SuppressLint("Registered")
class LoginActivity : Activity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var loginData : LoginUserData
    private lateinit var appContainer: AppContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * Use LoginViewModelFactory from AppContainer to create a LoginViewModel.
         */
        appContainer = (application as CoreApplication).appContainer
        // Login flow has started. populate loginContainer in AppContainer
        appContainer.loginContainer = LoginContainer(appContainer.userRepository)
        loginViewModel = appContainer.loginContainer!!.loginviewModelFactory.create()

        loginData = appContainer.loginContainer!!.loginUserData
    }

    override fun onDestroy() {
        // Login flow is finished Removing the instance of loginContainer in the AppContainer
        appContainer.loginContainer = null
        super.onDestroy()
    }

}