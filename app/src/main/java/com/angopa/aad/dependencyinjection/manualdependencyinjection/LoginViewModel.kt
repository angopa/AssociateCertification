package com.angopa.aad.dependencyinjection.manualdependencyinjection

import androidx.lifecycle.ViewModel

class LoginViewModel(
    private val userRepository: UserRepository
) : ViewModel()
