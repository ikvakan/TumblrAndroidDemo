package com.ikvakan.tumblrdemo.util.extensions

import com.ikvakan.tumblrdemo.data.remote.model.ResponseRemoteDto
import com.ikvakan.tumblrdemo.domain.model.Post

fun ResponseRemoteDto.toPostEntityList(): List<Post> {
    return this.posts.map { e ->
        Post(
            id = e.id,
            blogTitle = this.blog?.title ?: "",
            summary = e.summary,
            imageUrl = e.photos.firstOrNull()?.image?.url ?: ""
        )
    }
}
