package com.ikvakan.tumblrdemo.data.remote.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseRemoteDto(
    val posts: List<PostRemoteDto> = emptyList(),
    val blog: BlogRemoteDto? = null
)
