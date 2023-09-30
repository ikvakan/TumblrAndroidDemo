package com.ikvakan.tumblrdemo.core

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import java.lang.Exception

@Composable
fun BaseAppScreen(
    modifier: Modifier = Modifier,
    viewModel: BaseViewModel?,
    progress: Boolean = false,
    exception: Exception? = null,
    content: @Composable () -> Unit
) {
    if (viewModel != null) {
        LaunchedEffect(Unit) {
            Timber.d("monitoringNavigationFlow")
            viewModel.navigationFlow.collect { screen ->
                if (screen != null) {
                    Timber.tag("NAVIGATION").v("navigateTo: $screen")
                    viewModel.onNavigationHandled()
                }
            }
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column {
            content()
        }
        if (progress) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}
