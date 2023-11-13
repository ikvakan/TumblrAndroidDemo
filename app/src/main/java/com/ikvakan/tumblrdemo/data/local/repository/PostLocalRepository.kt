package com.ikvakan.tumblrdemo.data.local.repository

import com.ikvakan.tumblrdemo.data.local.dao.PostDao
import com.ikvakan.tumblrdemo.data.mapper.toEntity
import com.ikvakan.tumblrdemo.data.mapper.toLocalDto
import com.ikvakan.tumblrdemo.domain.model.Post

interface PostLocalRepository {

    suspend fun upsertPost(post: Post)
    suspend fun upsertPosts(posts: List<Post>)

    suspend fun getPosts(): List<Post>

    suspend fun getFavoritePosts(): List<Post>?

    suspend fun deletePost(postId: Long?)
}

class PostLocalRepositoryImpl(private val postDao: PostDao) : PostLocalRepository {
    override suspend fun upsertPost(post: Post) = postDao.upsertPost(post.toLocalDto())
    override suspend fun upsertPosts(posts: List<Post>) = postDao.upsertPosts(posts.map { it.toLocalDto() })

    override suspend fun getPosts(): List<Post> = postDao.getPosts().map { it.toEntity() }

    override suspend fun getFavoritePosts(): List<Post> = postDao.getFavoritePosts().map { it.toEntity() }
    override suspend fun deletePost(postId: Long?) = postDao.deletePost(postId)
}
