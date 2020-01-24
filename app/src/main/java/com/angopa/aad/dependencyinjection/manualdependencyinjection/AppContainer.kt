package com.angopa.aad.dependencyinjection.manualdependencyinjection

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Manual Dependency Injection, creating the required dependencies where we use the
 * loginViewModel class. Container of objects shared across the whole app.
 *
 * Check CoreApplication to check how was implemented.
 */
class AppContainer {
    // Since you want to expose userRepository out of the container, you need to satisfy its
    // dependencies
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://api.github.com")
        .build()

    private val userLocalDataSource = UserLocalDataSource()
    private val userRemoteDataSource = UserRemoteDataSource(retrofit)

    // userRepository is not private: it'll be exposed
    val userRepository = UserRepository(userRemoteDataSource, userLocalDataSource)

    var loginContainer: LoginContainer? = null
}