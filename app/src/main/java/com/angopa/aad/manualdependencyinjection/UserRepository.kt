package com.angopa.aad.manualdependencyinjection

class UserRepository(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSour: UserLocalDataSource
) {

}