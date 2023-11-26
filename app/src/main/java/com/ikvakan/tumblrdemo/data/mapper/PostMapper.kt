package com.ikvakan.tumblrdemo.data.mapper

import com.ikvakan.tumblrdemo.data.local.model.PostEntity
import com.ikvakan.tumblrdemo.data.remote.model.ResponseRemoteDto
import com.ikvakan.tumblrdemo.domain.model.Post

fun Post.toEntity() = PostEntity(
    postId = this.postId,
    blogTitle = this.blogTitle,
    summary = this.summary,
    imageUrl = this.imageUrl,
    isFavorite = this.isFavorite
)


fun PostEntity.toDomain() = Post(
    postId = this.postId,
    blogTitle = this.blogTitle,
    summary = this.summary,
    imageUrl = this.imageUrl,
    isFavorite = this.isFavorite
)

fun ResponseRemoteDto.toDomainList() = this.posts.map { e ->
    Post(
        postId = e.postId,
        blogTitle = this.blog?.title ?: "",
        summary = e.summary,
        imageUrl = e.photos.firstOrNull()?.image?.url ?: ""
    )
}

