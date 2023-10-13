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
    private val exceptionMapper: ExceptionMappers.Tumblr
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
            val favoritePosts = postRepository.getFavoritePostsFromDb()
            _uiState.update { state ->
                state.copy(
                    posts = posts,
                    favoritePosts = favoritePosts,
                    isRefreshing = false
                )
            }
        }
            .setExceptionMapper(exceptionMapper = exceptionMapper)
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
            deletePostFromDb(it)
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

    private fun deletePostFromDb(postId: Long?) {
        Timber.d("deletePostFromDb")
        postsJob?.cancel()

        postsJob = coroutine {
            postRepository.deletePostFromDb(postId)
        }.setExceptionMapper(exceptionMapper)
            .onException {
                Timber.e(it)
            }
            .launch()
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

    fun getAdditionalPosts() {
        Timber.d("getAdditionalPosts")
        postsJob?.cancel()

        if (_uiState.value.isLoadingMorePosts) {
            return
        }

        postsJob = coroutine {
            val additionalPosts =
                postRepository.getAdditionalPosts(offset = _uiState.value.posts?.size)
            _uiState.update {
                it.copy(
                    posts = additionalPosts,
                    isRefreshing = false
                )
            }
            Timber.d("post size:${_uiState.value.posts?.size}")
        }
            .setExceptionMapper(exceptionMapper = exceptionMapper)
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
        updateFavoritePosts(postId)
        setFavoritePostInDb(postId)
    }

    private fun setFavoritePostInDb(postId: Long?) {
        Timber.d("setFavoritePostInDb")
        postsJob?.cancel()
        postId?.let { id ->
            postsJob = coroutine {
                with(_uiState.value) {
                    val favoritePost = posts?.first { post -> post.id == id }
                    postRepository.setFavoritePostInDb(favoritePost)
                }
            }.setExceptionMapper(exceptionMapper)
                .onException {
                    Timber.e(it)
                }
                .launch()
        }
    }

    private fun filterFavoritePosts(): List<Post>? {
        return _uiState.value.posts?.filter { post -> post.isFavorite }
    }

    private fun updateFavoritePosts(postId: Long?) {
        postId.let {
            with(_uiState.value) {
                _uiState.update { state ->
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
                _uiState.update { state ->
                    state.copy(
                        favoritePosts = filterFavoritePosts()
                    )
                }
            }
        }
    }

    fun onRefresh() {
        _uiState.update { it.copy(isRefreshing = true) }
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
