package com.ikvakan.tumblrdemo.di.remote

import com.ikvakan.tumblrdemo.data.remote.repository.PostRemoteRepository
import com.ikvakan.tumblrdemo.data.remote.repository.PostRemoteRepositoryImpl
import com.ikvakan.tumblrdemo.domain.PostEntityMapper
import org.koin.dsl.module

val repositoryModule = module {

    single { PostEntityMapper() }
    single<PostRemoteRepository> { PostRemoteRepositoryImpl(postService = get(), mapper = get()) }
}
