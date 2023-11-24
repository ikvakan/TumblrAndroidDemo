package com.ikvakan.tumblrdemo.data.remote.repository

import com.ikvakan.tumblrdemo.data.mapper.toDomainList
import com.ikvakan.tumblrdemo.data.remote.service.PostService
import com.ikvakan.tumblrdemo.domain.model.Post
import timber.log.Timber

interface PostRemoteRepository {
    //    suspend fun getPosts(): ResponseWrapper<List<Post>>
    suspend fun getPosts(): List<Post>
  //  suspend fun getAdditionalPosts(offset: Int?): List<Post>
    suspend fun getPaginatedPosts(offset: Int?, limit: Int): List<Post>
}

class PostRemoteRepositoryImpl(
    private val postService: PostService,
) : PostRemoteRepository {
    //    override suspend fun getPosts(): ResponseWrapper<List<Post>> {
//        val data = postService.getPosts().response
//        return ResponseWrapper(
//            data = data.toEntityList(),
//            totalPosts = data.totalPosts
//        )
//    }
    override suspend fun getPosts(): List<Post> {
        val data = postService.getPosts().response
        return data.toDomainList()
    }

    //    override suspend fun getAdditionalPosts(offset: Int?): List<Post> {
//        val data = postService.getPosts(offset = (offset?.plus(PostService.LIMIT))).response
//        Timber.d("getAdditionalPostSize:${data.posts.size}")
//        return data.toDomainList()
//    }
//    override suspend fun getAdditionalPosts(offset: Int?): List<Post> {
//        val data = postService.getPosts(offset = offset).response
//        Timber.d("getAdditionalPostSize:${data.posts.size}")
//        return data.toDomainList()
//    }
    override suspend fun getPaginatedPosts(offset: Int?, limit: Int): List<Post> {
        val data = postService.getPosts(offset = offset, limit = limit).response
        Timber.d("getPaginatedPostsFromRemote:${data.posts.size}")
        return data.toDomainList()
    }
}
