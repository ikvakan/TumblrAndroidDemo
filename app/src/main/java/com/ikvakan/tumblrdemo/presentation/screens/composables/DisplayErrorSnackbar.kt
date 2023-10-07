package com.ikvakan.tumblrdemo.presentation.screens.composables

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.ikvakan.tumblrdemo.core.BaseViewModel
import com.ikvakan.tumblrdemo.util.extensions.getMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DisplayErrorSnackBar(
    exception: Exception,
    viewModel: BaseViewModel? = null,
    coroutineScope: CoroutineScope,
    snackBarHostState: SnackbarHostState,
    // onDismissed: () -> Unit
) {
    val context = LocalContext.current

    SnackbarHost(hostState = snackBarHostState) {
        LaunchedEffect(Unit) {
            coroutineScope.launch {
                val snackBarResult = snackBarHostState.showSnackbar(
                    message = exception.getMessage(context),
                    duration = SnackbarDuration.Indefinite
                )
                when (snackBarResult) {
                    SnackbarResult.Dismissed -> {}
                    else -> {}
                }
            }
        }
    }
}
