package com.ikvakan.tumblrdemo.di.local

import com.ikvakan.tumblrdemo.data.local.repository.PostLocalRepository
import com.ikvakan.tumblrdemo.data.local.repository.PostLocalRepositoryImpl
import org.koin.dsl.module

val localRepositoryModule = module {
    single<PostLocalRepository> { PostLocalRepositoryImpl(postDao = get()) }
}
