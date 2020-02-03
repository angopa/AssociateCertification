package com.angopa.aad.ui.dependencyinjection.daggercodelab.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.angopa.aad.dependencyinjection.daggerbasic.LoginViewModel
import com.angopa.aad.dependencyinjection.daggercodelab.user.UserManager
import org.junit.Before
import org.junit.Rule
import org.mockito.Mockito.mock

class LoginViewModelTest {
    // Executes each task synchronously using Architecture Components.
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()


    private lateinit var viewModel: LoginViewModel
    private lateinit var userManager: UserManager

    @Before
    fun setup() {
        userManager = mock(UserManager::class.java)
        viewModel = mock(LoginViewModel::class.java)
    }
}