package com.ikvakan.tumblrdemo.presentation.screens.posts

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.compose.LazyPagingItems
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ikvakan.tumblrdemo.R
import com.ikvakan.tumblrdemo.domain.model.Post
import com.ikvakan.tumblrdemo.presentation.Navigate
import com.ikvakan.tumblrdemo.presentation.screens.composables.PostListContent
import com.ikvakan.tumblrdemo.theme.TumblrDemoTheme

@Composable
fun PostsScreen(
    postsFlow: LazyPagingItems<Post>?,
    // posts: List<Post>?,
//    onFavoriteClick: (postId: Long?) -> Unit,
    onFavoriteClick: (Post?) -> Unit,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    isLoadingMorePosts: Boolean,
    onDeletePost: (Long?) -> Unit,
    onNavigate: Navigate
) {
    SwipeRefresh(
        modifier = Modifier.padding(dimensionResource(id = R.dimen.small_padding)),
        state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
        onRefresh = onRefresh,
        indicator = { state, refreshTrigger ->
            SwipeRefreshIndicator(
                state = state,
                refreshTriggerDistance = refreshTrigger,
                scale = true,
                shape = CircleShape
            )
        },
        content = {
            PostListContent(
                postsFlow = postsFlow,
                // posts = posts,
//                onFavoriteClick = onFavoriteClick,
                onFavoriteClick = onFavoriteClick,
                isLoadingMorePosts = isLoadingMorePosts,
                onDeletePost = onDeletePost,
                onNavigate = onNavigate
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PostsScreenPreview() {
    TumblrDemoTheme {
        PostsScreen(
            postsFlow = null,
            // posts = MockData().postEntities,
            onNavigate = {},
            isRefreshing = false,
            onRefresh = {},
            isLoadingMorePosts = false,
            onDeletePost = {},
            onFavoriteClick = {}
        )
    }
}
