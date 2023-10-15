package com.ikvakan.tumblrdemo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ikvakan.tumblrdemo.data.local.dao.PostDao
import com.ikvakan.tumblrdemo.data.local.model.PostLocalDto

@Database(entities = [PostLocalDto::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getPostDao(): PostDao
}
