package com.ikvakan.tumblrdemo.data.remote.repository

import com.ikvakan.tumblrdemo.data.remote.service.PostService
import com.ikvakan.tumblrdemo.domain.PostEntityMapper
import com.ikvakan.tumblrdemo.domain.model.PostEntity

class PostRemoteRepositoryImpl(
    private val postService: PostService,
    private val mapper: PostEntityMapper
) : PostRemoteRepository {
    override suspend fun getPosts(): List<PostEntity>? {
        val data = postService.getBlog().responseRemoteDto
        return data?.let { mapper.toEntityList(it) }
    }
}
