package com.ikvakan.tumblrdemo.data.remote.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BlogRemoteDto(
    val title: String = ""
)
