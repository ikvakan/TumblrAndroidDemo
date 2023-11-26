package com.ikvakan.tumblrdemo.data.remote.repository

import com.ikvakan.tumblrdemo.data.mapper.toDomainList
import com.ikvakan.tumblrdemo.data.remote.service.PostService
import com.ikvakan.tumblrdemo.domain.model.Post
import timber.log.Timber

interface PostRemoteRepository {
    suspend fun getPaginatedPosts(offset: Int?, limit: Int): List<Post>
}

class PostRemoteRepositoryImpl(
    private val postService: PostService,
) : PostRemoteRepository {
    override suspend fun getPaginatedPosts(offset: Int?, limit: Int): List<Post> {
        val data = postService.getPosts(offset = offset, limit = limit).response
        return data.toDomainList()
    }
}
