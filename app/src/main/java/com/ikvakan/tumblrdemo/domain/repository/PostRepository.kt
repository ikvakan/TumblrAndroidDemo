package com.ikvakan.tumblrdemo.domain.repository

import com.ikvakan.tumblrdemo.data.local.repository.PostLocalRepository
import com.ikvakan.tumblrdemo.data.remote.repository.PostRemoteRepository
import com.ikvakan.tumblrdemo.domain.model.Post
import timber.log.Timber

interface PostRepository {
    suspend fun getPosts(): List<Post>
    suspend fun getAdditionalPosts(offset: Int?): List<Post>

    suspend fun setFavoritePostInDb(post: Post?)
    suspend fun getFavoritePosts(): List<Post>?
}

class PostRepositoryImpl(
    private val remoteDataSource: PostRemoteRepository,
    private val localDataSource: PostLocalRepository
) : PostRepository {

    override suspend fun getPosts(): List<Post> {
        val postsDb = localDataSource.getPosts().reversed()
        Timber.d("postsDb:${postsDb.size}")
        return postsDb.ifEmpty {
            remoteDataSource.getPosts().also { remotePosts ->
                localDataSource.upsertPosts(remotePosts)
            }
        }
    }

    override suspend fun getAdditionalPosts(offset: Int?): List<Post> {
        val additionalPosts = remoteDataSource.getAdditionalPosts(offset = offset)
        Timber.d("additionalPosts:${additionalPosts.size}")
        return localDataSource.getPosts().reversed().also { localPosts ->
            localDataSource.upsertPosts(localPosts + additionalPosts)
        }
    }

    override suspend fun setFavoritePostInDb(post: Post?) {
        post?.let {
            localDataSource.upsertPost(it)
        }
    }

    override suspend fun getFavoritePosts(): List<Post>? = localDataSource.getFavoritePosts()
}
