package com.ikvakan.tumblrdemo.di.exception

import com.ikvakan.tumblrdemo.data.exception.ExceptionMappers
import com.ikvakan.tumblrdemo.data.exception.tumblr.TumblrExceptionMapper
import org.koin.dsl.module

val exceptionModule = module {
    single<ExceptionMappers.Tumblr> { TumblrExceptionMapper() }
}
