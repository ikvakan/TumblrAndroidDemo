package com.ikvakan.tumblrdemo.domain.repository

import com.ikvakan.tumblrdemo.domain.model.PostEntity

interface PostRepository {
    suspend fun getPosts(): List<PostEntity>
}
