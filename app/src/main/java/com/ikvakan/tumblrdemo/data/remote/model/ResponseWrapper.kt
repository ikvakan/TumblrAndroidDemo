package com.ikvakan.tumblrdemo.data.remote.model

data class ResponseWrapper<T>(
    val data: T,
    val totalPosts: Int
)
