package com.ikvakan.tumblrdemo.di.domain

import com.ikvakan.tumblrdemo.domain.usecase.PostUseCase
import com.ikvakan.tumblrdemo.domain.usecase.PostUseCaseImpl
import org.koin.dsl.module

val domainModule = module {
    single<PostUseCase> { PostUseCaseImpl(remoteDataSource = get(), localDataSource = get()) }
}
