package com.ichi.moviedatabase.core.extensions

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ichi.moviedatabase.views.fragments.BaseFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// Avoid some boilerplate code
fun BaseFragment<*>.repeatOnLifecycleStarted(block: suspend CoroutineScope.() -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            block()
        }
    }
}
