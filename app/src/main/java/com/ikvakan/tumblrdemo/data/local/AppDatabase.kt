package com.ikvakan.tumblrdemo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ikvakan.tumblrdemo.data.local.dao.PostDao
import com.ikvakan.tumblrdemo.data.local.model.PostEntity

@Database(entities = [PostEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getPostDao(): PostDao
}
