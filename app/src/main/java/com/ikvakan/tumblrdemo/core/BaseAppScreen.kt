package com.ikvakan.tumblrdemo.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ikvakan.tumblrdemo.presentation.navigation.Navigate
import java.lang.Exception

@Composable
fun BaseAppScreen(
    modifier: Modifier = Modifier,
    viewModel: BaseViewModel?,
    progress: Boolean = false,
    onNavigate: Navigate,
    exception: Exception? = null,
    content: @Composable () -> Unit
) {
}
