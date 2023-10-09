package com.ikvakan.tumblrdemo.presentation.screens.posts

import android.os.Build
import androidx.annotation.RequiresExtension
import com.ikvakan.tumblrdemo.core.BaseViewModel
import com.ikvakan.tumblrdemo.data.exception.ExceptionMappers
import com.ikvakan.tumblrdemo.domain.model.Post
import com.ikvakan.tumblrdemo.domain.repository.PostRepository
import com.ikvakan.tumblrdemo.util.extensions.coroutine
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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
    private val postRepository: PostRepository,
    private val remoteExceptionMapper: ExceptionMappers.Remote
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(PostsUiState())
    val uiState = _uiState.asStateFlow()

    private var postsJob: Job? = null

    init {
        getPosts()
    }

    private fun getPosts() {
        Timber.d("getPosts")
        postsJob?.cancel()

        postsJob = coroutine {
            val posts = postRepository.getPosts()
            _uiState.update {
                it.copy(
                    posts = posts,
                    favoritePosts = null,
                    isRefreshing = false
                )
            }
        }
            .setRemoteExceptionMapper(exceptionMapper = remoteExceptionMapper)
            .onProgressChanged { progress ->
                Timber.d("remote exception:${progress.exception}")
                _uiState.update {
                    it.updateProgressState(
                        progress = progress.inProgress,
                        exception = progress.exception
                    )
                }
            }
            .onException {
                Timber.e(it)
            }
            .launch()
    }

    fun onDeletePost(postId: Long?) {
        postId.let {
            val updatedPosts = _uiState.value.posts?.toMutableList()
            val updatedFavorites = _uiState.value.favoritePosts?.toMutableList()
            updatedPosts?.removeIf { post -> post.id == it }
            updatedFavorites?.removeIf { post -> post.id == it }

            _uiState.update { state ->
                state.copy(
                    posts = updatedPosts,
                    favoritePosts = updatedFavorites
                )
            }
        }
    }

    fun setSelectedPost(postId: Long?) {
        postId.let {
            val selectedPost = _uiState.value.posts?.firstOrNull { post ->
                post.id == it
            }
            Timber.d("selected post:$selectedPost")
            _uiState.update { state ->
                state.copy(
                    selectedPost = selectedPost
                )
            }
        }
    }

    fun getAdditionalItems() {
        Timber.d("getAdditionalItems")
        postsJob?.cancel()

        if (_uiState.value.isLoadingMorePosts) {
            return
        }

        postsJob = coroutine {
            val additionalPosts =
                postRepository.getAdditionalPosts(offset = _uiState.value.posts?.size)
            _uiState.update {
                with(_uiState.value) {
                    it.copy(
                        posts = posts?.plus(additionalPosts),
                        isRefreshing = false
                    )
                }
            }
            Timber.d("post size:${_uiState.value.posts?.size}")
        }
            .setRemoteExceptionMapper(exceptionMapper = remoteExceptionMapper)
            .onProgressChanged { progress ->
                _uiState.update {
                    it.updateProgressState(
                        isLoadingMorePosts = progress.inProgress,
                        exception = progress.exception
                    )
                }
            }
            .onException {
                Timber.e(it)
            }
            .launch()
    }

    fun toggleIsFavoritePost(postId: Long?) {
        postId.let {
            _uiState.update { state ->
                with(_uiState.value) {
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
            }
            _uiState.update { state ->
                state.copy(
                    favoritePosts = _uiState.value.posts?.filter { post -> post.isFavorite }
                )
            }
        }
    }

    fun onRefresh() {
        _uiState.update { it.copy(isRefreshing = true) }
        getPosts()
    }

    override fun onCleared() {
        postsJob?.cancel()
        Timber.d("onCleared:$this")
        super.onCleared()
    }
}
