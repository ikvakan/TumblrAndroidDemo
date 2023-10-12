package com.ikvakan.tumblrdemo.data.exception.tumblr

import android.os.Build
import androidx.annotation.RequiresExtension
import com.ikvakan.tumblrdemo.data.exception.ExceptionMappers
import retrofit2.HttpException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.sql.SQLException

class TumblrExceptionMapper : ExceptionMappers.Tumblr {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun map(e: Exception): TumblrException {
        var exception: TumblrException =
            TumblrException.UnknownException()
        when (e) {
            is SocketTimeoutException, is UnknownHostException -> {
                exception = TumblrException.NetworkException()
            }
            is HttpException -> {
                exception = getExceptionFromResponse(e)
            }
            is SQLException -> {
                exception = TumblrException.DatabaseException()
            }
        }
        return exception
    }

    private fun getExceptionFromResponse(httpException: HttpException): TumblrException {
        var exception: TumblrException = TumblrException.UnknownException()
        when (httpException.code()) {
            in HttpURLConnection.HTTP_BAD_REQUEST..HttpURLConnection.HTTP_UNSUPPORTED_TYPE -> {
                exception = TumblrException.ClientException()
            }

            in HttpURLConnection.HTTP_INTERNAL_ERROR..HttpURLConnection.HTTP_VERSION -> {
                exception = TumblrException.ServerException()
            }
        }
        return exception
    }
}
