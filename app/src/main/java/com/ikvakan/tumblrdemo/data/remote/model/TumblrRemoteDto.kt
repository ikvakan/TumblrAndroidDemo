package com.ikvakan.tumblrdemo.data.remote.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TumblrRemoteDto(
    val responseRemoteDto: ResponseRemoteDto? = null
)
