package com.ikvakan.tumblrdemo.di.network

import com.ikvakan.tumblrdemo.BuildConfig
import com.ikvakan.tumblrdemo.data.interceptors.ApiCallsInterceptor
import com.ikvakan.tumblrdemo.data.remote.service.PostService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber

val networkModule = module {

    single(named("API_BASE_URL")) { BuildConfig.API_BASE_URL }
    single(named("API_KEY")) { BuildConfig.API_KEY }

    // OkHttp client
    single {
        val loggingInterceptor = HttpLoggingInterceptor { message ->
            Timber.tag("HTTP").v(message)
        }
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
        OkHttpClient.Builder()
            .addNetworkInterceptor(loggingInterceptor)
            .addNetworkInterceptor(ApiCallsInterceptor(get(named("API_KEY"))))
            .build()
    }
    // Moshi
    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    // Retrofit
    single {
        Retrofit.Builder()
            .client(get())
            .baseUrl(get<String>(named("API_BASE_URL")))
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
    }

    single { get<Retrofit>().create(PostService::class.java) }
}
