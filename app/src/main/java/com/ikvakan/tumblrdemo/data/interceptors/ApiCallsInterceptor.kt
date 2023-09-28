package com.ikvakan.tumblrdemo.data.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class ApiCallsInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().apply {
            url(chain.request().url.newBuilder().addQueryParameter("api_key", apiKey).build())
        }.build()
        return chain.proceed(request)
    }
}
