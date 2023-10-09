package com.ikvakan.tumblrdemo.presentation.screens.posts

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ikvakan.tumblrdemo.data.mock.MockData
import com.ikvakan.tumblrdemo.domain.model.Post
import com.ikvakan.tumblrdemo.presentation.navigation.Navigate
import com.ikvakan.tumblrdemo.presentation.screens.composables.PostListContent
import com.ikvakan.tumblrdemo.theme.TumblrDemoTheme

@Composable
fun PostsScreen(
    paddingValues: PaddingValues,
    posts: List<Post>?,
    onFavoriteClick: (postId: Long?) -> Unit,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    isLoadingMorePosts: Boolean,
    onLoadMoreItems: () -> Unit,
    onDeletePost: (Long?) -> Unit,
    onNavigate: Navigate
) {
    if (posts != null) {
        SwipeRefresh(
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
                    posts = posts,
                    onFavoriteClick = onFavoriteClick,
                    paddingValues = paddingValues,
                    onLoadMoreItems = onLoadMoreItems,
                    isLoadingMorePosts = isLoadingMorePosts,
                    onDeletePost = onDeletePost,
                    onNavigate = onNavigate
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PostsScreenPreview() {
    TumblrDemoTheme {
        PostsScreen(
            paddingValues = PaddingValues(),
            posts = MockData().postEntities,
            onNavigate = {},
            isRefreshing = false,
            onRefresh = {},
            isLoadingMorePosts = false,
            onLoadMoreItems = {},
            onDeletePost = {},
            onFavoriteClick = {}
        )
    }
}
