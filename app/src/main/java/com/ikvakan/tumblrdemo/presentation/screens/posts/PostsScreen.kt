package com.ikvakan.tumblrdemo.presentation.screens.posts

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ikvakan.tumblrdemo.R
import com.ikvakan.tumblrdemo.data.mock.MockData
import com.ikvakan.tumblrdemo.domain.model.Post
import com.ikvakan.tumblrdemo.presentation.navigation.Navigate
import com.ikvakan.tumblrdemo.presentation.screens.composables.PostListContent
import com.ikvakan.tumblrdemo.theme.TumblrDemoTheme

@Composable
fun PostsScreen(
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
                    posts = posts,
                    onFavoriteClick = onFavoriteClick,
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
