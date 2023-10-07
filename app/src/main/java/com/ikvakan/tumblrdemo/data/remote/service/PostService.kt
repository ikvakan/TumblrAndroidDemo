package com.ikvakan.tumblrdemo.data.remote.service

import com.ikvakan.tumblrdemo.data.remote.model.TumblrRemoteDto
import retrofit2.http.GET
import retrofit2.http.Query

interface PostService {
    companion object {
        const val BLOG_IDENTIFIER = "marvelentertainment.tumblr.com"
        const val LIMIT = 20
    }

    @GET("blog/${BLOG_IDENTIFIER}/posts/")
    suspend fun getPosts(
        @Query("limit") limit: Int = LIMIT,
        @Query("offset") offset: Int? = 0,
        @Query("type") type: String = "photo"
    ): TumblrRemoteDto
}
