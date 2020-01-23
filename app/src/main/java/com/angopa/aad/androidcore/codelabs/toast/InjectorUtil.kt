package com.angopa.aad.androidcore.codelabs.toast

import androidx.lifecycle.ViewModelProvider
import com.angopa.aad.androidcore.codelabs.toast.viewmodel.ToastViewModelFactory

object InjectorUtil {
    fun provideToastActivityViewModelFactory(): ViewModelProvider.NewInstanceFactory = ToastViewModelFactory()
}