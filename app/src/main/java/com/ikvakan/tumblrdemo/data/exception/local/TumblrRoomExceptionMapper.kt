package com.ikvakan.tumblrdemo.data.exception.local

import com.ikvakan.tumblrdemo.data.exception.ExceptionMappers

class TumblrRoomExceptionMapper : ExceptionMappers.Room {
    override fun map(e: Exception): TumblrLocalException {
        TODO("Not yet implemented")
    }
}
