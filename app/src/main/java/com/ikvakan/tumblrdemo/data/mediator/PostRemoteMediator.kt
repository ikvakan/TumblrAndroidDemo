package com.ikvakan.tumblrdemo.data.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.ikvakan.tumblrdemo.data.exception.ExceptionMappers
import com.ikvakan.tumblrdemo.data.local.AppDatabase
import com.ikvakan.tumblrdemo.data.local.model.PostEntity
import com.ikvakan.tumblrdemo.data.mapper.toEntity
import com.ikvakan.tumblrdemo.data.remote.repository.PostRemoteRepository
import timber.log.Timber

const val INITIAL_OFFSET: Int = 0

@OptIn(ExperimentalPagingApi::class)
class PostRemoteMediator(
    private val remoteDataSource: PostRemoteRepository,
    private val database: AppDatabase,
    private val exceptionMapper: ExceptionMappers.Tumblr
) : RemoteMediator<Int, PostEntity>() {
    @Suppress("TooGenericExceptionCaught")
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PostEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> {
                    Timber.tag("LoadType").w("REFRESH:$INITIAL_OFFSET")
                    INITIAL_OFFSET
                }

                LoadType.PREPEND -> {
                    Timber.tag("LoadType").w("PREPEND")
                    return MediatorResult.Success(endOfPaginationReached = true)
                }

                LoadType.APPEND -> {
                    Timber.tag("LoadType").w("APPEND")
                    val lastItem = state.lastItemOrNull()

                    if (lastItem == null) {
                        INITIAL_OFFSET
                    } else {
                        Timber.tag("LoadType").w("APPEND - last item: ${lastItem.id}")

                        (lastItem.id.div(state.config.pageSize))
                    }
                }
            }

            val offset = loadKey.times(state.config.pageSize)
            Timber.tag("LoadType").w("OFFSET: $offset")
            val posts =
                remoteDataSource.getPaginatedPosts(offset = offset, limit = state.config.pageSize)
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.getPostDao().clearAll()
                    database.getPostDao().resetPrimaryKey()
                }
                val localPosts = posts.map { it.toEntity() }
                database.getPostDao().upsertPosts(localPosts)
            }

            return MediatorResult.Success(
                endOfPaginationReached = posts.isEmpty()
            )
        } catch (e: Exception) {
            MediatorResult.Error(exceptionMapper.map(e))
        }
    }
}
