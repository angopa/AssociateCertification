package com.angopa.aad.dependencyinjection.daggercodelab.di

import com.angopa.aad.dependencyinjection.daggercodelab.storage.SharedPreferencesStorage
import com.angopa.aad.dependencyinjection.daggercodelab.storage.Storage
import dagger.Binds
import dagger.Module

// Tells Dagger this is a Dagger module
@Module
abstract class StorageModule {

    // Makes Dagger provide SharedPreferencesStorage when a Storage type is requested
    @Binds
    abstract fun provideStorage(storage: SharedPreferencesStorage): Storage
}