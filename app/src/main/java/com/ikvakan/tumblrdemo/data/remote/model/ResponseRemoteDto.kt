package com.ikvakan.tumblrdemo.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseRemoteDto(
    val posts: List<PostRemoteDto> = emptyList(),
    val blog: BlogRemoteDto? = null,
    @Json(name = "total_posts")
    val totalPosts: Int = 0
)
