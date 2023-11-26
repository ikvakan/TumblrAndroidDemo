package com.ikvakan.tumblrdemo.presentation.screens.composables

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun LaunchErrorSnackBar(
    coroutineScope: CoroutineScope,
    snackBarHostState: SnackbarHostState,
    errorMessage: String,
    onDismiss: () -> Unit
) {
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            val snackBarResult = snackBarHostState.showSnackbar(
                message = errorMessage,
                duration = SnackbarDuration.Short
            )
            when (snackBarResult) {
                SnackbarResult.Dismissed -> onDismiss()
                else -> {}
            }
        }
    }
}
