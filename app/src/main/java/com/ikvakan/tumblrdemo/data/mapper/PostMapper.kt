package com.ikvakan.tumblrdemo.data.mapper

import com.ikvakan.tumblrdemo.data.local.model.PostLocalDto
import com.ikvakan.tumblrdemo.data.remote.model.ResponseRemoteDto
import com.ikvakan.tumblrdemo.domain.model.Post

fun Post.toLocalDto() = PostLocalDto(
    id = this.id,
    blogTitle = this.blogTitle,
    summary = this.summary,
    imageUrl = this.imageUrl,
    isFavorite = this.isFavorite
)


fun PostLocalDto.toEntity() = Post(
    id = this.id,
    blogTitle = this.blogTitle,
    summary = this.summary,
    imageUrl = this.imageUrl,
    isFavorite = this.isFavorite
)

fun ResponseRemoteDto.toEntityList() = this.posts.map { e ->
    Post(
        id = e.id,
        blogTitle = this.blog?.title ?: "",
        summary = e.summary,
        imageUrl = e.photos.firstOrNull()?.image?.url ?: ""
    )
}

