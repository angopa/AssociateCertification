package com.angopa.aad.manualdependencyinjection

import androidx.lifecycle.ViewModel

class LoginViewModel(
    private val userRepository: UserRepository
) : ViewModel()
