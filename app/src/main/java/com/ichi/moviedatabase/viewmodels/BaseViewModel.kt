package com.ichi.moviedatabase.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

open class BaseViewModel : ViewModel() {
    protected val _showLoader: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val showLoader: StateFlow<Boolean> get() = _showLoader.asStateFlow()

    protected val _error: MutableStateFlow<Int?> = MutableStateFlow(null)
    val error: StateFlow<Int?> get() = _error.asStateFlow()
}
