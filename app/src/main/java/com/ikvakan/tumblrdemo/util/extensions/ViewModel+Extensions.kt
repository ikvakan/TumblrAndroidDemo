package com.ikvakan.tumblrdemo.util.extensions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikvakan.tumblrdemo.util.Coroutine
import kotlinx.coroutines.CoroutineScope

fun ViewModel.coroutine(block: suspend CoroutineScope.() -> Unit): Coroutine =
    Coroutine(this.viewModelScope, block)
