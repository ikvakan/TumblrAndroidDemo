package com.ikvakan.tumblrdemo.core

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.ikvakan.tumblrdemo.presentation.navigation.Navigate
import com.ikvakan.tumblrdemo.util.extensions.getMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception

@Composable
fun BaseAppScreen(
    modifier: Modifier = Modifier,
    viewModel: BaseViewModel?,
    progress: Boolean = false,
    onNavigate: Navigate,
    coroutineScope: CoroutineScope,
    snackBarHostState: SnackbarHostState,
    exception: Exception? = null,
    topBar: @Composable () -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    val context = LocalContext.current

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
                    message = exception.getMessage(context = context),
                    onDismiss = {}
                )
            }

            Box(modifier = modifier.fillMaxSize()) {
                content(paddingValues)

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
                SnackbarResult.Dismissed -> onDismiss
                else -> {}
            }
        }
    }
}
