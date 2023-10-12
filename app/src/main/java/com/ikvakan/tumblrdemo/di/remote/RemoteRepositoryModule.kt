package com.ikvakan.tumblrdemo.di.remote

import com.ikvakan.tumblrdemo.data.remote.repository.PostRemoteRepository
import com.ikvakan.tumblrdemo.data.remote.repository.PostRemoteRepositoryImpl
import com.ikvakan.tumblrdemo.domain.repository.PostRepository
import com.ikvakan.tumblrdemo.domain.repository.PostRepositoryImpl
import org.koin.dsl.module

val remoteRepositoryModule = module {

    single<PostRemoteRepository> { PostRemoteRepositoryImpl(postService = get()) }

}
