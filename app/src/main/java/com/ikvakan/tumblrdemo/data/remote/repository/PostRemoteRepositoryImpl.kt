package com.ikvakan.tumblrdemo.data.remote.repository

import com.ikvakan.tumblrdemo.data.remote.service.PostService
import com.ikvakan.tumblrdemo.domain.model.PostEntity
import com.ikvakan.tumblrdemo.util.extensions.toPostEntityList

class PostRemoteRepositoryImpl(
    private val postService: PostService,
) : PostRemoteRepository {
    override suspend fun getPosts(): List<PostEntity> {
        val data = postService.getBlog().response
        return data.toPostEntityList()
    }
}
