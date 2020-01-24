package com.angopa.aad.dependencyinjection.manualdependencyinjection

class UserRepository(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSour: UserLocalDataSource
) {

}