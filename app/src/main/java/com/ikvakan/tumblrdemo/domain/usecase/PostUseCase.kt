package com.ikvakan.tumblrdemo.domain.usecase

import com.ikvakan.tumblrdemo.data.local.repository.PostLocalRepository
import com.ikvakan.tumblrdemo.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface PostUseCase {
    suspend fun setFavoritePostInDb(post: Post?)
    fun getFavoritePostsFromDb(): Flow<List<Post>>
    suspend fun deletePostFromDb(postId: Long?)
    suspend fun getSelectedPost(postId: Long?): Post
    suspend fun setFavoritePost(post: Post?)
}

class PostUseCaseImpl(
    private val localDataSource: PostLocalRepository
) : PostUseCase {
    override suspend fun setFavoritePostInDb(post: Post?) {
        post?.let {
            localDataSource.upsertPost(it)
        }
    }
    override fun getFavoritePostsFromDb(): Flow<List<Post>> =
        localDataSource.getFavoritePosts()
    override suspend fun deletePostFromDb(postId: Long?) = localDataSource.deletePost(postId)
    override suspend fun getSelectedPost(postId: Long?): Post =
        localDataSource.getSelectedPost(postId = postId)
    override suspend fun setFavoritePost(post: Post?) {
        post?.let {
            return localDataSource.setFavoritePost(it.postId, it.isFavorite)
        }
    }
}
