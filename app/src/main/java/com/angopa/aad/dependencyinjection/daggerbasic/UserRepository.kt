package com.angopa.aad.dependencyinjection.daggerbasic

import javax.inject.Inject
import javax.inject.Singleton

/**
 * @Inject lets Dagger know how to create instances of this object
 * Scope this class to a component using @Singleton scope (i.e. ApplicatoinGraph)
 */
@Singleton
class UserRepository @Inject constructor(
    private val userLocalDataSource: UserLocalDataSource,
    private val userRemoteDataSource: UserRemoteDataSource
) {
    fun loadData(): String = userRemoteDataSource.loadData()
}