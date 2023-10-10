package com.ikvakan.tumblrdemo.core.network

import android.content.Context
import com.ikvakan.tumblrdemo.util.extensions.observeNetworkConnectivity
import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {
    fun observe(): Flow<NetworkConnectionState>
}

class NetworkConnectivityObserver(private val context: Context) : ConnectivityObserver {
    override fun observe(): Flow<NetworkConnectionState> = context.observeNetworkConnectivity()
}
