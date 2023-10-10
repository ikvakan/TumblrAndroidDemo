package com.ikvakan.tumblrdemo.presentation.screens.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ikvakan.tumblrdemo.R
import com.ikvakan.tumblrdemo.theme.TumblrDemoTheme

@Composable
fun NetworkConnectivityStatusBar(
    isConnected: Boolean,
) {
    val backgroundColor =
        if (isConnected) {
            Color.Green
        } else {
            Color.Red
        }

    val message = if (isConnected) {
        stringResource(R.string.back_online)
    } else {
        stringResource(R.string.no_internet_connection)
    }

    val iconResource = if (isConnected) {
        R.drawable.ic_connectivity_available
    } else {
        R.drawable.ic_connectivity_unavailable
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = backgroundColor)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = iconResource),
                contentDescription = null,

            )
            Text(
                text = message
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NetworkConnectivityStatusBarPreview() {
    TumblrDemoTheme {
        NetworkConnectivityStatusBar(isConnected = true)
    }
}
