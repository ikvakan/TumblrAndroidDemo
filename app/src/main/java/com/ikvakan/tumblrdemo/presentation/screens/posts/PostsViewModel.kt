package com.ikvakan.tumblrdemo.presentation.screens.posts

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.ikvakan.tumblrdemo.core.BaseViewModel
import com.ikvakan.tumblrdemo.domain.model.Post
import com.ikvakan.tumblrdemo.domain.usecase.GetPaginatedPostsUseCase
import com.ikvakan.tumblrdemo.domain.usecase.PostUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import timber.log.Timber

data class PostsUiState(
    val postsFlow: Flow<PagingData<Post>>? = null,
    val progress: Boolean = false,
    val exception: Exception? = null,
    val selectedPost: Post? = null,
    val favoritePosts: List<Post>? = null,
    val isRefreshing: Boolean = false
) {
    fun updateProgressState(
        progress: Boolean = false,
        exception: Exception? = null
    ) =
        this.copy(
            progress = progress,
            exception = exception
        )
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class PostsViewModel(
    private val postUseCase: PostUseCase,
    private val getPaginatedPostsUseCase: GetPaginatedPostsUseCase
) : BaseViewModel<PostsUiState>(PostsUiState()) {

    private var postsJob: Job? = null

    init {
        getPosts()
        monitorFavoritePostsFlow()
    }

    private fun monitorFavoritePostsFlow() {
        postUseCase.getFavoritePostsFromDb().onEach {
            updateState { state ->
                state.copy(
                    favoritePosts = it
                )
            }
        }.onStart {
            Timber.d("monitorFavoritePostsFlow - started")
        }.onCompletion {
            Timber.d("monitorFavoritePostsFlow - completed")
        }.catch {
            Timber.e(it)
        }.launchIn(viewModelScope)
    }

    private fun getPosts() {
        updateState {
            it.copy(
                postsFlow = getPaginatedPostsUseCase(viewModelScope)
            )
        }
    }

    fun toggleIsRefreshing(isRefreshing: Boolean) {
        updateState {
            it.copy(
                isRefreshing = isRefreshing
            )
        }
    }
    fun onDeletePost(postId: Long?) {
        postId?.let {
            deletePostFromDb(it)
        }
    }

    private fun deletePostFromDb(postId: Long?) {
        Timber.d("deletePostFromDb")
        postsJob?.cancel()

        postsJob = launchCoroutine(
            coroutineBlock = {
                postUseCase.deletePostFromDb(postId = postId)
            },
            onException = {
                Timber.e(it)
            },
            onProgressChanged = {
                updateState { state ->
                    state.updateProgressState(
                        exception = it.exception
                    )
                }
            }
        )
    }

    fun setSelectedPost(postId: Long?) {
        Timber.d("getSelectedPost")
        postsJob?.cancel()
        postId?.let {
            postsJob = launchCoroutine(
                coroutineBlock = {
                    val selectedPost = postUseCase.getSelectedPost(it)
                    updateState { state ->
                        state.copy(
                            selectedPost = selectedPost
                        )
                    }
                },
                onException = { ex ->
                    Timber.e(ex)
                },
                onProgressChanged = {
                    updateState { state ->
                        state.updateProgressState(
                            progress = it.inProgress,
                            exception = it.exception
                        )
                    }
                }
            )
        }
    }
    fun toggleIsFavoritePost(post: Post?) {
        Timber.d("toggleFavorites")
        setFavoritePostInDb(post)
    }

    private fun setFavoritePostInDb(post: Post?) {
        Timber.d("setFavoritePostInDb")
        postsJob?.cancel()

        post?.let {
            postsJob = launchCoroutine(
                coroutineBlock = {
                    val favPost = it.copy(postId = it.postId, isFavorite = !it.isFavorite)
                    Timber.d("fav post: $favPost")
                    postUseCase.setFavoritePost(favPost)
                },
                onException = {
                    Timber.e(it)
                },
                onProgressChanged = { progress ->
                    updateState { state ->
                        state.updateProgressState(
                            exception = progress.exception
                        )
                    }
                }
            )
        }
    }

    override fun onConnectionRestored(isConnected: Boolean) {
        if (isConnected) {
            getPosts()
        }
    }

    override fun onCleared() {
        postsJob?.cancel()
        super.onCleared()
        Timber.d("onCleared:$this")
    }
}
