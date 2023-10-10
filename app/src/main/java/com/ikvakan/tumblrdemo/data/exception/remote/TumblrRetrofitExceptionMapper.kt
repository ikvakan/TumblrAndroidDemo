package com.ikvakan.tumblrdemo.data.exception.remote

import android.os.Build
import androidx.annotation.RequiresExtension
import com.ikvakan.tumblrdemo.data.exception.ExceptionMappers
import retrofit2.HttpException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class TumblrRetrofitExceptionMapper : ExceptionMappers.Retrofit {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun map(e: Exception): TumblrRemoteException {
        var exception: TumblrRemoteException =
            TumblrRemoteException.UnknownRemoteException()
        when (e) {
            is SocketTimeoutException, is UnknownHostException -> {
                exception = TumblrRemoteException.NetworkException()
            }
            is HttpException -> {
                exception = getExceptionFromResponse(e)
            }
        }
        return exception
    }

    private fun getExceptionFromResponse(httpException: HttpException): TumblrRemoteException {
        var exception: TumblrRemoteException = TumblrRemoteException.UnknownRemoteException()
        when (httpException.code()) {
            in HttpURLConnection.HTTP_BAD_REQUEST..HttpURLConnection.HTTP_UNSUPPORTED_TYPE -> {
                exception = TumblrRemoteException.ClientException()
            }

            in HttpURLConnection.HTTP_INTERNAL_ERROR..HttpURLConnection.HTTP_VERSION -> {
                exception = TumblrRemoteException.ServerException()
            }
        }
        return exception
    }
}
