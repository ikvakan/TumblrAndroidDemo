package com.ikvakan.tumblrdemo.util.extensions

import com.ikvakan.tumblrdemo.data.remote.model.ResponseRemoteDto
import com.ikvakan.tumblrdemo.domain.model.PostEntity

fun ResponseRemoteDto.toPostEntityList(): List<PostEntity> {
    return this.posts.map { e ->
        PostEntity(
            id = e.id,
            blogTitle = this.blog?.title ?: "",
            summary = e.summary,
            imageUrl = e.photos.firstOrNull()?.image?.url ?: ""
        )
    }.toList()
}
