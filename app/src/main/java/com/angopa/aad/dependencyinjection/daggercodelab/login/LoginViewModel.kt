package com.angopa.aad.dependencyinjection.daggercodelab.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.angopa.aad.dependencyinjection.daggerbasic.ActivityScope
import com.angopa.aad.dependencyinjection.daggercodelab.user.UserManager
import javax.inject.Inject

/**
 * LoginViewModel is the ViewModel that [LoginActivity] uses to
 * obtain information of what to show on the screen and handle complex logic.
 */
@ActivityScope
class LoginViewModel @Inject constructor(private val userManager: UserManager) : ViewModel() {
    private val _loginState = MutableLiveData<LoginViewState>()
    val loginState: LiveData<LoginViewState>
        get() = _loginState

    fun login(userName: String, password: String) {
        if (userManager.loginUser(userName, password)) {
            _loginState.value = LoginSuccess
        } else {
            _loginState.value = LoginError
        }
    }

    fun unregister() {
        userManager.unregister()
    }

    fun getUserName(): String = userManager.userName
}