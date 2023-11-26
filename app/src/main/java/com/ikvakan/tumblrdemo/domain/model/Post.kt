package com.ikvakan.tumblrdemo.domain.model

data class Post(
    val postId: Long? = null,
    val blogTitle: String = "",
    val summary: String = "",
    val imageUrl: String = "",
    val isFavorite: Boolean = false
)
