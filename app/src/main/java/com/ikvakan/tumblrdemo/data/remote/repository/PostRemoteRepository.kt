package com.ikvakan.tumblrdemo.data.remote.repository

import com.ikvakan.tumblrdemo.data.remote.service.PostService
import com.ikvakan.tumblrdemo.domain.model.Post
import com.ikvakan.tumblrdemo.util.extensions.toPostEntityList
import timber.log.Timber

interface PostRemoteRepository {
    suspend fun getPosts(): List<Post>
    suspend fun getAdditionalPosts(offset: Int?): List<Post>
}

class PostRemoteRepositoryImpl(
    private val postService: PostService,
) : PostRemoteRepository {
    override suspend fun getPosts(): List<Post> {
        val data = postService.getPosts().response
        return data.toPostEntityList()
    }

    override suspend fun getAdditionalPosts(offset: Int?): List<Post> {
        val data = postService.getPosts(offset = (offset?.plus(PostService.LIMIT))).response
        Timber.d("getAdditionalPostSize:${data.posts.size}")
        return data.toPostEntityList()
    }
}
