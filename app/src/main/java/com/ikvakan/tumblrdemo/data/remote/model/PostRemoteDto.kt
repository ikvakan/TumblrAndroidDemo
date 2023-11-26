package com.ikvakan.tumblrdemo.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostRemoteDto(
    @Json(name = "id")
    val postId: Long? = null,
    val summary: String = "",
    val photos: List<PhotoRemoteDto> = emptyList()
)
