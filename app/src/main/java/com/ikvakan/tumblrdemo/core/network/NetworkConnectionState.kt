package com.ikvakan.tumblrdemo.core.network

sealed class NetworkConnectionState {
    object Connected : NetworkConnectionState()
    object Disconnected : NetworkConnectionState()
}