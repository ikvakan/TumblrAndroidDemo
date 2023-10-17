package com.ikvakan.tumblrdemo.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.ikvakan.tumblrdemo.data.local.model.PostLocalDto

@Dao
interface PostDao {
    @Upsert
    suspend fun upsertPost(post: PostLocalDto)

    @Upsert
    suspend fun upsertPosts(posts: List<PostLocalDto>)

    @Query("SELECT * FROM posts")
    suspend fun getPosts(): List<PostLocalDto>

    @Query("SELECT * FROM posts WHERE isFavorite =1")
    suspend fun getFavoritePosts(): List<PostLocalDto>

    @Query("DELETE FROM posts WHERE id = :postId")
    suspend fun deletePost(postId: Long?)
}
