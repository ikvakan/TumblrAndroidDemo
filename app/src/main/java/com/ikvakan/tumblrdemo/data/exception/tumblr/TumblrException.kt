package com.ikvakan.tumblrdemo.data.exception.tumblr

sealed class TumblrException : Exception() {
    class ClientException : TumblrException()
    class ServerException : TumblrException()
    class NetworkException : TumblrException()
    class DatabaseException : TumblrException()
    class UnknownException : TumblrException()
}
