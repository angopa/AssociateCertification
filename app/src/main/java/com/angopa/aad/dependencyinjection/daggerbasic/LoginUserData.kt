package com.angopa.aad.dependencyinjection.daggerbasic

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR

class LoginUserData: BaseObservable() {
    private var userName: String? = null

    @Bindable
    fun getUserName(): String? {
        return userName
    }

    fun setUserName(userName: String) {
        this.userName = userName
        notifyPropertyChanged(BR.userName)
    }
}