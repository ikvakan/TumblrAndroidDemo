package com.ikvakan.tumblrdemo.data.exception

interface ExceptionMapper<out T> {
    fun map(e: Exception): T

}
