package com.ikvakan.tumblrdemo.di.domain

import com.ikvakan.tumblrdemo.domain.repository.PostRepository
import com.ikvakan.tumblrdemo.domain.repository.PostRepositoryImpl
import org.koin.dsl.module

val domainModule = module {
    single<PostRepository> { PostRepositoryImpl(remoteDataSource = get()) }
}
