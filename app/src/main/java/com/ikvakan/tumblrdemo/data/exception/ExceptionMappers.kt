package com.ikvakan.tumblrdemo.data.exception

import com.ikvakan.tumblrdemo.data.exception.local.TumblrLocalException
import com.ikvakan.tumblrdemo.data.exception.remote.TumblrRemoteException

interface ExceptionMappers {
    interface Retrofit : ExceptionMapper<TumblrRemoteException>
    interface Room : ExceptionMapper<TumblrLocalException>
}
