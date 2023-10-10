package com.ikvakan.tumblrdemo.presentation.screens.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.ikvakan.tumblrdemo.core.network.NetworkConnectionState
import kotlinx.coroutines.delay

const val NetworkDelay = 500L

@Composable
fun AppConnectivityStatus(
    connectionState: NetworkConnectionState,
    onConnectionRestored: (Boolean) -> Unit
) {
    val isConnected = connectionState === NetworkConnectionState.Connected
    var visibility by remember { mutableStateOf(false) }

    AnimatedVisibility(
        visible = visibility,
        enter = expandVertically(),
        exit = shrinkVertically(),
        content = { NetworkConnectivityStatusBar(isConnected = isConnected) }
    )

    LaunchedEffect(isConnected) {
        visibility = if (!isConnected) {
            true
        } else {
            delay(NetworkDelay)
            onConnectionRestored.invoke(visibility)
            false
        }
    }
}
