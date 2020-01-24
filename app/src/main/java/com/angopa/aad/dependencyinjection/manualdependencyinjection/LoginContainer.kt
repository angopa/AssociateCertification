package com.angopa.aad.dependencyinjection.manualdependencyinjection

class LoginContainer (private val userRepository: UserRepository) {
    val loginUserData = LoginUserData()

    val loginviewModelFactory = LoginViewModelFactory(userRepository)
}