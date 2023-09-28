package com.ikvakan.tumblrdemo.domain.repository

import com.ikvakan.tumblrdemo.data.remote.repository.PostRemoteRepository
import com.ikvakan.tumblrdemo.domain.model.PostEntity

class PostRepositoryImpl(private val remoteDataSource: PostRemoteRepository) : PostRepository {
    override suspend fun getPosts(): List<PostEntity> {
        return remoteDataSource.getPosts()
    }
}
