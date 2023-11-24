package com.ikvakan.tumblrdemo.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.ikvakan.tumblrdemo.data.local.model.PostEntity
import com.ikvakan.tumblrdemo.data.mapper.toDomain
import com.ikvakan.tumblrdemo.domain.model.Post
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetPaginatedPostsUseCase(
    private val postsPager: Pager<Int, PostEntity>
) {
    operator fun invoke(scope: CoroutineScope): Flow<PagingData<Post>> =
        postsPager.flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }.cachedIn(scope)


}
