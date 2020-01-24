package com.angopa.aad.dependencyinjection.manualdependencyinjection

class LoginViewModelFactory(private val userRepository: UserRepository) : Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : LoginViewModel> create(): T {
        return LoginViewModel(userRepository) as T
    }
}