package com.ikvakan.tumblrdemo.presentation.screens.posts

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import com.ikvakan.tumblrdemo.core.BaseViewModel
import com.ikvakan.tumblrdemo.data.mapper.toDomain
import com.ikvakan.tumblrdemo.domain.model.Post
import com.ikvakan.tumblrdemo.domain.usecase.GetPaginatedPostsUseCase
import com.ikvakan.tumblrdemo.domain.usecase.PostUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import timber.log.Timber

data class PostsUiState(
   // val posts: List<Post>? = null,
    val postsFlow: Flow<PagingData<Post>>? = null,
    val progress: Boolean = false,
    val exception: Exception? = null,
    // val exception: Throwable? = null,
    val selectedPost: Post? = null,
    val favoritePosts: List<Post>? = null
  //  val favoritePosts: Flow<List<Post>>? = null
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
    val posts = getPaginatedPostsUseCase(viewModelScope)



    init {
        getPosts()

    }

    private fun getPosts() {
        Timber.d("getPosts")
        postsJob?.cancel()

        postsJob = launchCoroutine(
            coroutineBlock = {
//                val posts = postUseCase.getPosts()
              //  val posts = getPaginatedPostsUseCase(viewModelScope)
                val favoritePosts = postUseCase.getFavoritePostsFromDb().collect {
                    Timber.d("favorite Posts flow: $it")
                    updateState { state ->
                        state.copy(
                            favoritePosts = it
                        )
                    }
                }
            },
            onException = {
                Timber.e(it)
            },
            onProgressChanged = { progress ->
                updateState {
                    it.updateProgressState(
                        //  progress = progress.inProgress,
                        exception = progress.exception
                    )
                }
            }
        )
    }

    sealed interface Result<out T> {
        data class Success<T>(val data: T) : Result<T>
        data class Error(val exception: Throwable? = null) : Result<Nothing>
        object Loading : Result<Nothing>
    }

    fun <T> Flow<T>.asResult(): Flow<Result<T>> {
        return this
            .map<T, Result<T>> {
                Result.Success(it)
            }
            .onStart { emit(Result.Loading) }
            .catch { emit(Result.Error(it)) }
    }

    fun onDeletePost(postId: Long?) {
        postId?.let {
            deletePostFromDb(it)
//            val updatedPosts = uiState.value.posts?.toMutableList()
//            val updatedFavorites = uiState.value.favoritePosts?.toMutableList()
//            updatedPosts?.removeIf { post -> post.postId == it }
//            updatedFavorites?.removeIf { post -> post.postId == it }
//            updateState { state ->
//                state.copy(
//                    posts = updatedPosts,
//                    favoritePosts = updatedFavorites
//                )
//            }
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

//    fun setSelectedPost(postId: Long?) {
//        postId?.let {
//            val selectedPost = uiState.value.posts?.firstOrNull { post ->
//                post.postId == it
//            }
//            updateState { state ->
//                state.copy(
//                    selectedPost = selectedPost
//                )
//            }
//        }
//    }

//    fun toggleIsFavoritePost(postId: Long?) {
//        Timber.d("toggleFavorites")
// //        updateFavoritePosts(postId)
//        setFavoritePostInDb(postId)
//    }
    fun toggleIsFavoritePost(post: Post?) {
        Timber.d("toggleFavorites")
//        updateFavoritePosts(postId)
        setFavoritePostInDb(post)
    }

    private fun setFavoritePostInDb(post: Post?) {
        Timber.d("setFavoritePostInDb")
        postsJob?.cancel()

        post?.let {
            postsJob = launchCoroutine(
                coroutineBlock = {
                    with(uiState.value) {
//                           val favoritePost = posts?.first { post -> post.postId == id }
//                          postUseCase.setFavoritePostInDb(favoritePost)

//                        val updatePostsFlow =
//                            postsFlow?.map { pagingData ->
//                                pagingData.map { post ->
//                                    if (post.postId == postId) {
//                                        post.copy(
//                                            isFavorite = !post.isFavorite
//                                        )
//                                    } else {
//                                        post
//                                    }
//                                }
//                            }
//
//                        updateState {
//                            it.copy(
//                                postsFlow = updatePostsFlow
//                            )
//                        }

                        val favPost = it.copy(postId = it.postId, isFavorite = !it.isFavorite)
                        Timber.d("fav post: $favPost")
                        postUseCase.setFavoritePost(favPost)
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
//
//    private fun filterFavoritePosts(): List<Post>? {
//        return uiState.value.posts?.filter { post -> post.isFavorite }
//    }
//
//    private fun updateFavoritePosts(postId: Long?) {
//        postId.let {
//            with(uiState.value) {
//                updateState { state ->
//                    state.copy(
//                        posts = posts?.map { post ->
//                            if (post.postId == it) {
//                                post.copy(isFavorite = !post.isFavorite)
//                            } else {
//                                post
//                            }
//                        },
//                        selectedPost = selectedPost?.copy(isFavorite = !selectedPost.isFavorite)
//                            ?: selectedPost,
//                    )
//                }
//                updateState { state ->
//                    state.copy(
//                        favoritePosts = filterFavoritePosts()
//                    )
//                }
//            }
//        }
//    }

    override fun onConnectionRestored(isConnected: Boolean) {
        if (isConnected) getPosts()
    }

    override fun onCleared() {
        postsJob?.cancel()
        super.onCleared()
        Timber.d("onCleared:$this")
    }
}
