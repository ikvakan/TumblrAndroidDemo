package com.ikvakan.tumblrdemo.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class PostLocalDto(
    @PrimaryKey(autoGenerate = false)
    val id: Long? = null,
    val blogTitle: String = "",
    val summary: String = "",
    val imageUrl: String = "",
    val isFavorite: Boolean = false
)
