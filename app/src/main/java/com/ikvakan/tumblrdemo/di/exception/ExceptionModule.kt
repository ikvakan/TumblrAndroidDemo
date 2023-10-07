package com.ikvakan.tumblrdemo.di.exception

import com.ikvakan.tumblrdemo.data.exception.ExceptionMappers
import com.ikvakan.tumblrdemo.data.exception.local.TumblrLocalExceptionMapper
import com.ikvakan.tumblrdemo.data.exception.remote.TumblrRemoteExceptionMapper
import org.koin.dsl.module

val exceptionModule = module {
    single<ExceptionMappers.Remote> { TumblrRemoteExceptionMapper() }
    single<ExceptionMappers.Local> { TumblrLocalExceptionMapper() }
}
