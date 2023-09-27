package com.ikvakan.tumblrdemo.domain.model

data class PostEntity(
    val id: Int? = null,
    val blogTitle: String = "",
    val summary: String = "",
    val imageUrl: String = "",
    val isFavorite: Boolean = false
)
