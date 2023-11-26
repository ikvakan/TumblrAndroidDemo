package com.ikvakan.tumblrdemo.core

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.ikvakan.tumblrdemo.presentation.Navigate
import com.ikvakan.tumblrdemo.presentation.screens.composables.AppConnectivityStatus
import com.ikvakan.tumblrdemo.presentation.screens.composables.LaunchErrorSnackBar
import com.ikvakan.tumblrdemo.util.extensions.currentNetworkState
import com.ikvakan.tumblrdemo.util.extensions.getErrorMessage
import kotlinx.coroutines.CoroutineScope
import timber.log.Timber

@Composable
fun <S> BaseAppScreen(
    modifier: Modifier = Modifier,
    viewModel: BaseViewModel<S>,
    progress: Boolean = false,
    onNavigate: Navigate,
    coroutineScope: CoroutineScope,
    exception: Exception? = null,
    topBar: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        Timber.d("monitoringNavigationFlow")
        viewModel.navigationFlow.collect { screen ->
            if (screen != null) {
                Timber.tag("NAVIGATION").v("navigateTo: $screen")
                onNavigate(screen)
                viewModel.onNavigationHandled()
            }
        }
    }

    // observe network connection state from the view model flow
    val connectionState by produceState(initialValue = context.currentNetworkState) {
        viewModel.networkConnectivityFlow.collect {
            value = it
            Timber.d("Network connection state: $value")
        }
    }

    Scaffold(
        topBar = topBar,
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        content = { paddingValues ->

            if (exception != null) {
                LaunchErrorSnackBar(
                    coroutineScope = coroutineScope,
                    snackBarHostState = snackBarHostState,
                    errorMessage = exception.getErrorMessage(context = context),
                    onDismiss = { }
                )
            }

            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Column {
                    AppConnectivityStatus(
                        connectionState = connectionState,
                        onConnectionRestored = { viewModel.onConnectionRestored(it) }
                    )
                    content()
                }

                if (progress) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    )
}
