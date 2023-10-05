package com.ikvakan.tumblrdemo.domain.model

data class Post(
    val id: Long? = null,
    val blogTitle: String = "",
    val summary: String = "",
    val imageUrl: String = "",
    val isFavorite: Boolean = false
)
