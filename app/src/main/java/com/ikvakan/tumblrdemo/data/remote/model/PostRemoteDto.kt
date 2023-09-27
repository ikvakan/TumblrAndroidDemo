package com.ikvakan.tumblrdemo.data.remote.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostRemoteDto(
    val id: Int? = null,
    val summary: String = "",
    val photos: List<PhotoRemoteDto> = emptyList()
)
