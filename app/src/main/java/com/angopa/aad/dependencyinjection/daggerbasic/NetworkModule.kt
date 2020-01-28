package com.angopa.aad.dependencyinjection.daggerbasic

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * @Module inform Dagger that this class is a Dagger Module
 */
@Module
class NetworkModule {
    // @Provides tell Dagger how to create instances of the type that this function return
    // (i.e. LoginRetrofitService)
    // Function parameters are the dependencies of this type
    @Provides
    fun providesLoginRetrofitService(): Retrofit {
        // Whenever Dagger needs to provide an instance of type LoginRetrofitService
        // this code (the one inside the @Provides method) is run
        return Retrofit.Builder()
            .baseUrl("https://github.com")
            .build()
    }
}