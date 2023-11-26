package com.ikvakan.tumblrdemo.di.app

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.ikvakan.tumblrdemo.data.local.AppDatabase
import com.ikvakan.tumblrdemo.data.mediator.PostRemoteMediator
import com.ikvakan.tumblrdemo.data.remote.service.PostService
import org.koin.dsl.module

@OptIn(ExperimentalPagingApi::class)
val mediatorModule = module {

    single {
        Pager(
            config = PagingConfig(
                pageSize = PostService.LIMIT
            ),
            remoteMediator = PostRemoteMediator(remoteDataSource = get(), database = get(), exceptionMapper = get()),
            pagingSourceFactory = {
                get<AppDatabase>().getPostDao().getPaginatedPosts()
            }
        )
    }
}
