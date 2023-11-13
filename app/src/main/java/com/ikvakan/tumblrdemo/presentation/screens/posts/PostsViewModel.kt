package com.ikvakan.tumblrdemo.presentation.screens.posts

import android.os.Build
import androidx.annotation.RequiresExtension
import com.ikvakan.tumblrdemo.core.BaseViewModel
import com.ikvakan.tumblrdemo.domain.model.Post
import com.ikvakan.tumblrdemo.domain.usecase.PostUseCase
import kotlinx.coroutines.Job
import timber.log.Timber

data class PostsUiState(
    val posts: List<Post>? = null,
    val progress: Boolean = false,
    val exception: Exception? = null,
    val selectedPost: Post? = null,
    val favoritePosts: List<Post>? = null,
    val isLoadingMorePosts: Boolean = false,
    val isRefreshing: Boolean = false
) {
    fun updateProgressState(
        progress: Boolean = false,
        isLoadingMorePosts: Boolean = false,
        exception: Exception? = null
    ) =
        this.copy(
            progress = progress,
            isLoadingMorePosts = isLoadingMorePosts,
            exception = exception
        )
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class PostsViewModel(
    private val postUseCase: PostUseCase
) : BaseViewModel<PostsUiState>(PostsUiState()) {

    private var postsJob: Job? = null

    init {
        getPosts()
    }

    private fun getPosts() {
        Timber.d("getPosts")
        postsJob?.cancel()

        postsJob = launchCoroutine(
            coroutineBlock = {
                val posts = postUseCase.getPosts()
                val favoritePosts = postUseCase.getFavoritePostsFromDb()
                updateState { state ->
                    state.copy(
                        posts = posts,
                        favoritePosts = favoritePosts,
                        isRefreshing = false
                    )
                }
            },
            onException = {
                Timber.e(it)
            },
            onProgressChanged = { progress ->
                updateState {
                    it.updateProgressState(
                        progress = progress.inProgress,
                        exception = progress.exception
                    )
                }
            }
        )
    }

    fun onDeletePost(postId: Long?) {
        postId.let {
            deletePostFromDb(it)
            val updatedPosts = uiState.value.posts?.toMutableList()
            val updatedFavorites = uiState.value.favoritePosts?.toMutableList()
            updatedPosts?.removeIf { post -> post.id == it }
            updatedFavorites?.removeIf { post -> post.id == it }
            updateState { state ->
                state.copy(
                    posts = updatedPosts,
                    favoritePosts = updatedFavorites
                )
            }
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
                    state.copy(
                        exception = it.exception
                    )
                }
            }
        )
    }

    fun setSelectedPost(postId: Long?) {
        postId.let {
            val selectedPost = uiState.value.posts?.firstOrNull { post ->
                post.id == it
            }
            Timber.d("selected post:$selectedPost")
            updateState { state ->
                state.copy(
                    selectedPost = selectedPost
                )
            }
        }
    }

    fun getAdditionalPosts() {
        Timber.d("getAdditionalPosts")
        postsJob?.cancel()

        if (uiState.value.isLoadingMorePosts) {
            return
        }

        postsJob = launchCoroutine(
            coroutineBlock = {
                val additionalPosts =
                    postUseCase.getAdditionalPosts(offset = uiState.value.posts?.size)
                updateState {
                    it.copy(
                        posts = additionalPosts,
                        isRefreshing = false
                    )
                }
            },
            onException = {
                Timber.e(it)
            },
            onProgressChanged = { progress ->
                updateState { state ->
                    state.updateProgressState(
                        progress = progress.inProgress,
                        exception = progress.exception
                    )
                }
            }
        )
    }

    fun toggleIsFavoritePost(postId: Long?) {
        updateFavoritePosts(postId)
        setFavoritePostInDb(postId)
    }

    private fun setFavoritePostInDb(postId: Long?) {
        Timber.d("setFavoritePostInDb")
        postsJob?.cancel()
        postId?.let { id ->
            postsJob = launchCoroutine(
                coroutineBlock = {
                    with(uiState.value) {
                        val favoritePost = posts?.first { post -> post.id == id }
                        postUseCase.setFavoritePostInDb(favoritePost)
                    }
                },
                onException = {
                    Timber.e(it)
                },
                onProgressChanged = { progress ->
                    updateState { state ->
                        state.copy(
                            exception = progress.exception
                        )
                    }
                }
            )
        }
    }

    private fun filterFavoritePosts(): List<Post>? {
        return uiState.value.posts?.filter { post -> post.isFavorite }
    }

    private fun updateFavoritePosts(postId: Long?) {
        postId.let {
            with(uiState.value) {
                updateState { state ->
                    state.copy(
                        posts = posts?.map { post ->
                            if (post.id == it) {
                                post.copy(isFavorite = !post.isFavorite)
                            } else {
                                post
                            }
                        },
                        selectedPost = selectedPost?.copy(isFavorite = !selectedPost.isFavorite)
                            ?: selectedPost,
                    )
                }
                updateState { state ->
                    state.copy(
                        favoritePosts = filterFavoritePosts()
                    )
                }
            }
        }
    }

    fun onRefresh() {
        updateState { it.copy(isRefreshing = true) }
        getPosts()
    }

    override fun onConnectionRestored(isConnected: Boolean) {
        if (isConnected) getPosts()
    }

    override fun onCleared() {
        postsJob?.cancel()
        super.onCleared()
        Timber.d("onCleared:$this")
    }
}
