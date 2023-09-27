package com.ikvakan.tumblrdemo.data.remote.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImageRemoteDto(
    val url: String = ""
)
