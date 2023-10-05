package com.ikvakan.tumblrdemo.domain.repository

import com.ikvakan.tumblrdemo.data.remote.repository.PostRemoteRepository
import com.ikvakan.tumblrdemo.domain.model.Post

interface PostRepository {
    suspend fun getPosts(): List<Post>
    suspend fun getAdditionalPosts(offset: Int?): List<Post>
}

class PostRepositoryImpl(
    private val remoteDataSource: PostRemoteRepository
) : PostRepository {

    override suspend fun getPosts(): List<Post> {
        return remoteDataSource.getPosts()
    }

    override suspend fun getAdditionalPosts(offset: Int?): List<Post> {
        return remoteDataSource.getAdditionalPosts(offset = offset)
    }
}
