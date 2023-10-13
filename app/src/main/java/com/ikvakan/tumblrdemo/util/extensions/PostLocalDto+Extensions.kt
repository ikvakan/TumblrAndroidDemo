package com.ikvakan.tumblrdemo.util.extensions

import com.ikvakan.tumblrdemo.data.local.model.PostLocalDto
import com.ikvakan.tumblrdemo.domain.model.Post

fun PostLocalDto.toPost(): Post {
    return Post(
        id = this.id,
        blogTitle = this.blogTitle,
        summary = this.summary,
        imageUrl = this.imageUrl,
        isFavorite = this.isFavorite
    )
}
