package com.angopa.aad.androidui.codelabs.paging.firebase

/**
 *  Created by Andres Gonzalez on 03/16/2020.
 */
sealed class ViewModelState
data class Error(val message: String) : ViewModelState()
data class SuccessMessage(val message: String) : ViewModelState()
object Success : ViewModelState()