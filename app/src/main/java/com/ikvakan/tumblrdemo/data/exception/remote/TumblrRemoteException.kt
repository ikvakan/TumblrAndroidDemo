package com.ikvakan.tumblrdemo.data.exception.remote

sealed class TumblrRemoteException : Exception() {
    class ClientException : TumblrRemoteException()

    class ServerException : TumblrRemoteException()

    class NetworkException : TumblrRemoteException()

    class UnknownRemoteException : TumblrRemoteException()
}
