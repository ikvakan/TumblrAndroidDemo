package com.ikvakan.tumblrdemo.core

import androidx.lifecycle.ViewModel
import com.ikvakan.tumblrdemo.core.network.ConnectivityObserver
import com.ikvakan.tumblrdemo.presentation.navigation.AppScreen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import timber.log.Timber

/**
 * Works with [BaseAppScreen] composable to provide [navigateTo] navigation from view model.
 * Also used to observe [NetworkConnectionState] that is consumed in the [BaseAppScreen]
 * */
abstract class BaseViewModel : ViewModel(), KoinComponent {

    /**
     * Navigation flow that is monitored by [BaseAppScreen] and through which view model
     * can navigate to other screens with [navigateTo].
     * */
    private val _navigationFlow = MutableStateFlow<AppScreen?>(null)
    val navigationFlow: Flow<AppScreen?> = _navigationFlow

    protected fun navigateTo(screen: AppScreen) {
        Timber.d("navigate to: $screen")
        _navigationFlow.value = screen
    }

    /** Must be called by consumer of [navigationFlow] when navigation to screen is triggered. */
    fun onNavigationHandled() {
        Timber.d("onNavigationHandled")
        _navigationFlow.value = null
    }

    /**
     * Callback flow which emits [NetworkConnectionState] values
     * depending on the network availability
     */
    private val networkConnectivityObserver: ConnectivityObserver by lazy { get() }
    val networkConnectivityFlow = networkConnectivityObserver.observe()

    abstract fun onConnectionRestored(isConnected: Boolean)
}
