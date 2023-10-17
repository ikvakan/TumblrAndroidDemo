package com.ikvakan.tumblrdemo.core

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.ikvakan.tumblrdemo.presentation.navigation.Navigate
import com.ikvakan.tumblrdemo.presentation.screens.composables.AppConnectivityStatus
import com.ikvakan.tumblrdemo.util.extensions.currentNetworkState
import com.ikvakan.tumblrdemo.util.extensions.getErrorMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception

/**
 * Composable that is used as a wrapper - parent to other composables that represent other app screens.
 * Executes navigation from [BaseViewModel] and displays progress state as well as error messages in the snackbar.
 * Monitors network state and collects the flow from the [BaseViewModel].
 */
@Composable
fun BaseAppScreen(
    modifier: Modifier = Modifier,
    viewModel: BaseViewModel?,
    progress: Boolean = false,
    onNavigate: Navigate,
    coroutineScope: CoroutineScope,
    exception: Exception? = null,
    topBar: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    // handles navigation from view model
    if (viewModel != null) {
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
    }

    // observe network connection state from the view model flow
    val connectionState by produceState(initialValue = context.currentNetworkState) {
        viewModel?.networkConnectivityFlow?.collect {
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
                LaunchSnackBar(
                    coroutineScope = coroutineScope,
                    snackBarHostState = snackBarHostState,
                    message = exception.getErrorMessage(context = context),
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
                        onConnectionRestored = { viewModel?.onConnectionRestored(it) }
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

@Composable
fun LaunchSnackBar(
    coroutineScope: CoroutineScope,
    snackBarHostState: SnackbarHostState,
    message: String,
    onDismiss: () -> Unit
) {
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            val snackBarResult = snackBarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Short
            )
            when (snackBarResult) {
                SnackbarResult.Dismissed -> onDismiss()
                else -> {}
            }
        }
    }
}
