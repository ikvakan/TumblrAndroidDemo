package com.ikvakan.tumblrdemo.di.local

import androidx.room.Room
import com.ikvakan.tumblrdemo.BuildConfig
import com.ikvakan.tumblrdemo.data.local.AppDatabase
import org.koin.core.qualifier.named
import org.koin.dsl.module

val databaseModule = module {

    single(named("APP_DATABASE")) { BuildConfig.APP_DATABASE }
    single {
        Room.databaseBuilder(
            context = get(),
            klass = AppDatabase::class.java,
            name = get(named("APP_DATABASE"))
        ).build()
    }
    single { get<AppDatabase>().getPostDao() }
}
