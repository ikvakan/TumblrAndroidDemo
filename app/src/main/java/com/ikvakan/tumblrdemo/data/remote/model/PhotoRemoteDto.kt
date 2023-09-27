package com.ikvakan.tumblrdemo.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PhotoRemoteDto(
    @Json(name = "original_size")
    val image: ImageRemoteDto? = null
)
