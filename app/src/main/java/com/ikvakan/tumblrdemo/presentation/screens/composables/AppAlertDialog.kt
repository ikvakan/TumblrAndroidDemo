package com.ikvakan.tumblrdemo.presentation.screens.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ikvakan.tumblrdemo.R
import com.ikvakan.tumblrdemo.theme.TumblrDemoTheme

@Composable
fun AppAlertDialog(
    modifier: Modifier = Modifier,
    onDismiss: (Boolean) -> Unit,
    onConfirm: (Boolean) -> Unit
) {
    AlertDialog(
        modifier = modifier.padding(dimensionResource(id = R.dimen.medium_padding)),
        onDismissRequest = { onDismiss(false) },
        title = {
            AlertDialogTitle()
        },
        text = {
            Text(
                text = stringResource(R.string.are_you_sure_you_want_to_delete_this_post),
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(false)
                },
                content = {
                    Text(
                        text = stringResource(R.string.yes)
                    )
                }
            )
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismiss(false)
                },
                content = {
                    Text(
                        text = stringResource(R.string.cancel)
                    )
                }
            )
        }
    )
}

@Composable
fun AlertDialogTitle() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.delete_post_dialog_title),
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = null,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AlertDialogTitlePreview() {
    TumblrDemoTheme {
        AlertDialogTitle()
    }
}

@Preview(showBackground = true)
@Composable
fun AppAlertDialogPreview() {
    TumblrDemoTheme {
        AppAlertDialog(
            onDismiss = {},
            onConfirm = {}
        )
    }
}
