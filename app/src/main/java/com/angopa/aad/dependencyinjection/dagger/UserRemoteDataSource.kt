package com.angopa.aad.dependencyinjection.dagger


import android.os.Handler
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val loginRetrofitService: LoginRetrofitService
) {
    private var handler: Handler? = null
    private lateinit var runnable: Runnable

    init {
        handler = Handler()
    }

    fun loadData(): String =
        try {
            // Should call loginRetrofitService in order to obtain the UserData
            "My Name"
        } catch (exception: Exception) {
            "Exception"
        }
}