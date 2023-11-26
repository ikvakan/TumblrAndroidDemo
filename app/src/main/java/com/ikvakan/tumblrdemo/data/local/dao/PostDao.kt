package com.ikvakan.tumblrdemo.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.ikvakan.tumblrdemo.data.local.model.PostEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {
    @Upsert
    suspend fun upsertPost(post: PostEntity)

    @Upsert
    suspend fun upsertPosts(posts: List<PostEntity>)

    @Query("SELECT * FROM posts")
    suspend fun getPosts(): List<PostEntity>

    @Query("SELECT * FROM posts")
    fun getPaginatedPosts(): PagingSource<Int, PostEntity>

//    @Query("SELECT * FROM posts WHERE isFavorite =1")
//    suspend fun getFavoritePosts(): List<PostEntity>
    @Query("SELECT * FROM posts WHERE isFavorite =1")
    fun getFavoritePosts(): Flow<List<PostEntity>>

    @Query("DELETE FROM posts WHERE postId = :postId")
    suspend fun deletePost(postId: Long?)

    @Query("DELETE FROM posts")
    suspend fun clearAll()

    @Query("SELECT * FROM posts WHERE postId=:postId")
    suspend fun getSelectedPost(postId: Long?): PostEntity

    @Query("UPDATE posts SET isFavorite = :isFavorite WHERE postId = :postId")
    suspend fun setFavoritePost(postId: Long?, isFavorite: Boolean)

    @Query("DELETE FROM sqlite_sequence WHERE name = 'posts'")
    fun resetPrimaryKey()
}
