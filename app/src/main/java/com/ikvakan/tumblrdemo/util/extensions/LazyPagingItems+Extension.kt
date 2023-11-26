package com.ikvakan.tumblrdemo.util.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import timber.log.Timber

@Suppress("TopLevelPropertyNaming")
const val LAZY_PAGING_ITEMS = "LazyPagingItems"

@Composable
fun <T : Any> LazyPagingItems<T>?.getException(): Exception? {
    var loadState by remember {
        mutableStateOf(this?.loadState)
    }
    Timber.tag(LAZY_PAGING_ITEMS).w("loadState:$loadState")
    var exception by remember {
        mutableStateOf<Exception?>(null)
    }
    LaunchedEffect(key1 = loadState) {
        snapshotFlow { this@getException?.loadState }
            .collect {
                loadState = it
                exception = if (loadState?.refresh is LoadState.Error) {
                    (loadState?.refresh as LoadState.Error).error as Exception
                } else {
                    null
                }
            }
    }
    Timber.tag(LAZY_PAGING_ITEMS).w("exception:$exception")
    return exception
}
