package com.ikvakan.tumblrdemo.core

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.lifecycle.ViewModel
import com.ikvakan.tumblrdemo.core.network.ConnectivityObserver
import com.ikvakan.tumblrdemo.data.exception.ExceptionMappers
import com.ikvakan.tumblrdemo.presentation.AppScreen
import com.ikvakan.tumblrdemo.util.CoroutineBlockCallBack
import com.ikvakan.tumblrdemo.util.ExceptionCallBack
import com.ikvakan.tumblrdemo.util.ProgressChangedCallback
import com.ikvakan.tumblrdemo.util.extensions.coroutine
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject
import timber.log.Timber

abstract class BaseViewModel<S>(initialState: S) : ViewModel(), KoinComponent {

    private val exceptionMapper: ExceptionMappers.Tumblr by inject()

    private val _uiState = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    protected fun updateState(updater: (S) -> S) {
        _uiState.update(updater)
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    protected fun launchCoroutine(
        coroutineBlock: CoroutineBlockCallBack,
        onException: ExceptionCallBack,
        onProgressChanged: ProgressChangedCallback = {},
    ) = coroutine {
        coroutineBlock()
    }
        .setExceptionMapper(exceptionMapper)
        .onException(onException)
        .onProgressChanged(onProgressChanged)
        .launch()

    private val _navigationFlow = MutableStateFlow<AppScreen?>(null)
    val navigationFlow: Flow<AppScreen?> = _navigationFlow
    protected fun navigateTo(screen: AppScreen) {
        Timber.d("navigate to: $screen")
        _navigationFlow.value = screen
    }

    fun onNavigationHandled() {
        Timber.d("onNavigationHandled")
        _navigationFlow.value = null
    }

    private val networkConnectivityObserver: ConnectivityObserver by lazy { get() }
    val networkConnectivityFlow = networkConnectivityObserver.observe()

    abstract fun onConnectionRestored(isConnected: Boolean)

}
