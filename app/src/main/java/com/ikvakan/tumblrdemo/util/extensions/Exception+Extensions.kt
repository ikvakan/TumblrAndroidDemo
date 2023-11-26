package com.ikvakan.tumblrdemo.util.extensions

import android.content.Context
import com.ikvakan.tumblrdemo.R
import com.ikvakan.tumblrdemo.data.exception.tumblr.TumblrException

fun Throwable.getErrorMessage(context: Context): String {
    return when (this) {
        is TumblrException.ClientException ->
            context.getString(R.string.error_client)

        is TumblrException.ServerException ->
            context.getString(R.string.error_server_unavailable)

        is TumblrException.NetworkException ->
            context.getString(R.string.error_network)

        is TumblrException.DatabaseException -> {
            context.getString(R.string.error_database)
        }

        is TumblrException.UnknownException ->
            context.getString(R.string.error_general)

        else -> context.getString(R.string.error_general)
    }
}
