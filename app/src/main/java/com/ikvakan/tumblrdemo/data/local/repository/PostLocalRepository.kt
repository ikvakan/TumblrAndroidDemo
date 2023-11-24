package com.ikvakan.tumblrdemo.data.local.repository

import androidx.paging.PagingSource
import com.ikvakan.tumblrdemo.data.local.dao.PostDao
import com.ikvakan.tumblrdemo.data.local.model.PostEntity
import com.ikvakan.tumblrdemo.data.mapper.toDomain
import com.ikvakan.tumblrdemo.data.mapper.toEntity
import com.ikvakan.tumblrdemo.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface PostLocalRepository {

    suspend fun upsertPost(post: Post)
    suspend fun upsertPosts(posts: List<Post>)

    suspend fun getPosts(): List<Post>

    fun getFavoritePosts(): Flow<List<PostEntity>>

    suspend fun deletePost(postId: Long?)

    fun getPaginatedPosts(): PagingSource<Int, PostEntity>
    suspend fun setFavoritePost(postId: Long?, isFavorite: Boolean)
}

class PostLocalRepositoryImpl(private val postDao: PostDao) : PostLocalRepository {
    override suspend fun upsertPost(post: Post) = postDao.upsertPost(post.toEntity())
    override suspend fun upsertPosts(posts: List<Post>) =
        postDao.upsertPosts(posts.map { it.toEntity() })

    override suspend fun getPosts(): List<Post> = postDao.getPosts().map { it.toDomain() }

//    override suspend fun getFavoritePosts():  List<Post> =
//        postDao.getFavoritePosts().map { it.toDomain() }
    override fun getFavoritePosts(): Flow<List<PostEntity>> =
        postDao.getFavoritePosts()

    override suspend fun deletePost(postId: Long?) = postDao.deletePost(postId)
    override fun getPaginatedPosts(): PagingSource<Int, PostEntity> = postDao.getPaginatedPosts()
    override suspend fun setFavoritePost(postId: Long?, isFavorite: Boolean) =
        postDao.setFavoritePost(
            postId,
            isFavorite
        )
}
