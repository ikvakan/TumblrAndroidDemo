package com.ikvakan.tumblrdemo.data.exception

import com.ikvakan.tumblrdemo.data.exception.tumblr.TumblrException

interface ExceptionMappers {
    interface Tumblr : ExceptionMapper<TumblrException>
}
