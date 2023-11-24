package com.ikvakan.tumblrdemo.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class PostEntity(
    //    val id: Int = 0,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val postId: Long? = null,
    val blogTitle: String = "",
    val summary: String = "",
    val imageUrl: String = "",
    val isFavorite: Boolean = false
)
