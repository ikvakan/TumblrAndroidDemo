package com.ikvakan.tumblrdemo.di.domain

import com.ikvakan.tumblrdemo.domain.usecase.GetPaginatedPostsUseCase
import com.ikvakan.tumblrdemo.domain.usecase.PostUseCase
import com.ikvakan.tumblrdemo.domain.usecase.PostUseCaseImpl
import org.koin.dsl.module

val useCaseModule = module {
    single<PostUseCase> {
        PostUseCaseImpl(
            localDataSource = get()
        )
    }
    single { GetPaginatedPostsUseCase(postsPager = get()) }
}
