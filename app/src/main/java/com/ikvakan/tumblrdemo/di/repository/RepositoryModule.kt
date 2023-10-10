package com.ikvakan.tumblrdemo.di.repository

import com.ikvakan.tumblrdemo.data.remote.repository.PostRemoteRepository
import com.ikvakan.tumblrdemo.data.remote.repository.PostRemoteRepositoryImpl
import com.ikvakan.tumblrdemo.domain.repository.PostRepository
import com.ikvakan.tumblrdemo.domain.repository.PostRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {

    single<PostRemoteRepository> { PostRemoteRepositoryImpl(postService = get()) }
    single<PostRepository> { PostRepositoryImpl(remoteDataSource = get()) }
}
