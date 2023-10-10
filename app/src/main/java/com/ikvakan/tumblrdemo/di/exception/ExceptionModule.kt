package com.ikvakan.tumblrdemo.di.exception

import com.ikvakan.tumblrdemo.data.exception.ExceptionMappers
import com.ikvakan.tumblrdemo.data.exception.local.TumblrRoomExceptionMapper
import com.ikvakan.tumblrdemo.data.exception.remote.TumblrRetrofitExceptionMapper
import org.koin.dsl.module

val exceptionModule = module {
    single<ExceptionMappers.Retrofit> { TumblrRetrofitExceptionMapper() }
    single<ExceptionMappers.Room> { TumblrRoomExceptionMapper() }
}
