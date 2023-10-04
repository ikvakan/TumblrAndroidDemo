package com.ikvakan.tumblrdemo.presentation.screens.posts

import com.ikvakan.tumblrdemo.core.BaseViewModel
import com.ikvakan.tumblrdemo.domain.model.PostEntity
import com.ikvakan.tumblrdemo.domain.repository.PostRepository
import com.ikvakan.tumblrdemo.util.extensions.coroutine
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber

data class PostsUiState(
    val posts: List<PostEntity>? = null,
    val progress: Boolean = false,
    val exception: Exception? = null,
    val selectedPost: PostEntity? = null
) {
    fun updateProgressState(progress: Boolean = false, exception: Exception? = null) = this.copy(
        progress = progress,
        exception = exception
    )
}

class PostsViewModel(private val postRepository: PostRepository) :
    BaseViewModel() {

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
            Timber.d("posts:$posts")
            _uiState.update {
                it.copy(
                    posts = posts
                )
            }
        }
            .onProgressChanged { progress ->
                _uiState.update {
                    it.updateProgressState(
                        progress = progress.inProgress,
                        exception = progress.exception
                    )
                }
            }
            .onStarted { Timber.d("started") }
            .onCanceled { Timber.d("canceled") }
            .onException { Timber.e(it) }
            .onFinish { Timber.d("finished") }
            .launch()
    }

    fun setSelectedPost(postId: Long?) {
        val selectedPost = _uiState.value.posts?.firstOrNull { post ->
            post.id == postId
        }
        Timber.d("selected post:$selectedPost")
        _uiState.update { state ->
            state.copy(
                selectedPost = selectedPost
            )
        }
    }

    fun toggleIsFavoritePost(postId: Long?) {
        _uiState.update { state ->
            with(_uiState.value) {
                state.copy(
                    posts = this.posts?.map { post ->
                        if (post.id == postId) {
                            post.copy(isFavorite = !post.isFavorite)
                        } else {
                            post
                        }
                    },
                    selectedPost = if (this.selectedPost != null) {
                        this.selectedPost.copy(isFavorite = !this.selectedPost.isFavorite)
                    } else {
                        this.selectedPost
                    }
                )
            }
        }
    }

    override fun onCleared() {
        postsJob?.cancel()
        super.onCleared()
        Timber.d("onCleared:$this")
    }
}
