package com.angopa.aad.codelabs.toast

import androidx.lifecycle.ViewModelProvider
import com.angopa.aad.codelabs.toast.viewmodel.ToastViewModelFactory

object InjectorUtil {
    fun provideToastActivityViewModelFactory(): ViewModelProvider.NewInstanceFactory = ToastViewModelFactory()
}