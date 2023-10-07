package com.ikvakan.tumblrdemo.data.exception.local

sealed class TumblrLocalException : Exception() {
    class UnknownLocalException : TumblrLocalException()
}