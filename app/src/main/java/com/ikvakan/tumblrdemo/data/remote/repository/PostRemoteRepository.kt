package com.ikvakan.tumblrdemo.data.remote.repository

import com.ikvakan.tumblrdemo.domain.model.PostEntity

interface PostRemoteRepository {
    suspend fun getPosts(): List<PostEntity>?
}
