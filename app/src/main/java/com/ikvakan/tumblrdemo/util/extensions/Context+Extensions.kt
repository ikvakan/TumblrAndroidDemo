package com.ikvakan.tumblrdemo.util.extensions

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.widget.Toast
import com.ikvakan.tumblrdemo.core.network.NetworkConnectionState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

/**
 * Gets current network state in the [BaseAppScreen] composable
 */

val Context.currentNetworkState: NetworkConnectionState
    get() {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return getCurrentNetworkState(connectivityManager)
    }

/**
 * Flow that monitors network capability and observes connection state defined in the [NetworkConnectivityObserver]
 * and is consumed in the [BaseAppScreen] through [BaseViewModel]
 */

fun Context.observeNetworkConnectivity() = callbackFlow {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val callback = networkCallback { connectionState -> trySend(connectionState) }
    val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .build()

    connectivityManager.registerNetworkCallback(networkRequest, callback)

    // Set current state
    val currentState = getCurrentNetworkState(connectivityManager)
    trySend(currentState)

    // Remove callback when not used
    awaitClose { connectivityManager.unregisterNetworkCallback(callback) }
}

private fun getCurrentNetworkState(connectivityManager: ConnectivityManager): NetworkConnectionState {
    val connected = connectivityManager.allNetworks.any { network ->
        connectivityManager.getNetworkCapabilities(network)
            ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            ?: false
    }
    return if (connected) NetworkConnectionState.Connected else NetworkConnectionState.Disconnected
}

private fun networkCallback(callback: (NetworkConnectionState) -> Unit): ConnectivityManager.NetworkCallback {
    return object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            callback(NetworkConnectionState.Connected)
        }

        override fun onLost(network: Network) {
            callback(NetworkConnectionState.Disconnected)
        }
    }
}
