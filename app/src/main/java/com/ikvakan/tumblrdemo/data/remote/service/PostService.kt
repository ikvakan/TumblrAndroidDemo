package com.ikvakan.tumblrdemo.data.remote.service

import com.ikvakan.tumblrdemo.data.remote.model.TumblrRemoteDto
import retrofit2.http.GET
import retrofit2.http.Query

interface PostService {
    companion object {
        private const val BLOG_IDENTIFIER = "marvelentertainment.tumblr.com"
    }

    @GET("blog/${BLOG_IDENTIFIER}/posts/")
    fun getBlog(
        @Query("limit") limit: Int = 10,
        @Query("type") type: String = "photo"
    ): TumblrRemoteDto
}
