package com.angopa.aad.dependencyinjection.dagger

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

/**
 * A unique instance of LoginViewModel is provided in Components annotated with @ActivityScope
 */
@ActivityScope
class LoginViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
    private val currentName: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun loadData(): LiveData<String> {
        currentName.value = userRepository.loadData()
        return currentName
    }

    fun loadDataFromUsernameFragment() : LiveData<String> {
        currentName.value = userRepository.loadData()
        return currentName
    }

    fun loadDataFromPasswordFragment() : LiveData<String> {
        currentName.value = userRepository.loadData()
        return currentName
    }
}