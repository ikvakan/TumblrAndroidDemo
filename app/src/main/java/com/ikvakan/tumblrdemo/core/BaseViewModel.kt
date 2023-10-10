package com.ikvakan.tumblrdemo.core

import androidx.lifecycle.ViewModel
import com.ikvakan.tumblrdemo.core.network.ConnectivityObserver
import com.ikvakan.tumblrdemo.presentation.navigation.AppScreen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import timber.log.Timber

abstract class BaseViewModel : ViewModel(), KoinComponent {

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
