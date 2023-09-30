package com.ikvakan.tumblrdemo.data.remote.model

import com.squareup.moshi.JsonClass
import java.math.BigInteger

@JsonClass(generateAdapter = true)
data class PostRemoteDto(
    val id: Long? = null,
    val summary: String = "",
    val photos: List<PhotoRemoteDto> = emptyList()
)
