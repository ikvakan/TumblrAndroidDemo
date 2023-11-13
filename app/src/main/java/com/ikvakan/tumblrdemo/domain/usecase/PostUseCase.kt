package com.ikvakan.tumblrdemo.domain.usecase

import com.ikvakan.tumblrdemo.data.local.repository.PostLocalRepository
import com.ikvakan.tumblrdemo.data.remote.repository.PostRemoteRepository
import com.ikvakan.tumblrdemo.domain.model.Post
import timber.log.Timber

interface PostUseCase {
    suspend fun getPosts(): List<Post>
    suspend fun getAdditionalPosts(offset: Int?): List<Post>
    suspend fun setFavoritePostInDb(post: Post?)
    suspend fun getFavoritePostsFromDb(): List<Post>?
    suspend fun deletePostFromDb(postId: Long?)
}

class PostUseCaseImpl(
    private val remoteDataSource: PostRemoteRepository,
    private val localDataSource: PostLocalRepository
) : PostUseCase {
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

    override suspend fun getFavoritePostsFromDb(): List<Post>? = localDataSource.getFavoritePosts()
    override suspend fun deletePostFromDb(postId: Long?) = localDataSource.deletePost(postId)
}
