package com.ikvakan.tumblrdemo.util.extensions

import android.content.Context
import com.ikvakan.tumblrdemo.R
import com.ikvakan.tumblrdemo.data.exception.local.TumblrLocalException
import com.ikvakan.tumblrdemo.data.exception.remote.TumblrRemoteException

fun Exception.getMessage(context: Context): String {
    return when (this) {
        is TumblrRemoteException -> {
            when (this) {
                is TumblrRemoteException.ClientException ->
                    context.getString(R.string.error_client)

                is TumblrRemoteException.ServerException ->
                    context.getString(R.string.error_server_unavailable)

                is TumblrRemoteException.NetworkException ->
                    context.getString(R.string.error_network)

                is TumblrRemoteException.UnknownRemoteException ->
                    context.getString(R.string.error_general)
            }
        }

        is TumblrLocalException -> ""
        else -> ""
    }
}
