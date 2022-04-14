package com.muchbeer.ktorplug.utility.collectflow

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun <T> Fragment.collectFlowActivity (flowActivity: Flow<T>, collect: suspend (T)->Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flowActivity.collect {
                collect(it)
            }
        }
    }
}

fun <T> Fragment.collectStateFlow (stateFlow: StateFlow<T>, collect: suspend (T)->Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
               stateFlow.collectLatest {
                collect(it)
            }
        }
    }
}
