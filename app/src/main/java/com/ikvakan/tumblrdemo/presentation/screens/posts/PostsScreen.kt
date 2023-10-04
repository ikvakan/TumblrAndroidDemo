package com.ikvakan.tumblrdemo.presentation.screens.posts

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ikvakan.tumblrdemo.R
import com.ikvakan.tumblrdemo.data.mock.MockData
import com.ikvakan.tumblrdemo.domain.model.PostEntity
import com.ikvakan.tumblrdemo.presentation.navigation.Navigate
import com.ikvakan.tumblrdemo.presentation.screens.composables.AppTopBar
import com.ikvakan.tumblrdemo.presentation.screens.composables.NavigationIconType
import com.ikvakan.tumblrdemo.presentation.screens.composables.PostListContent
import com.ikvakan.tumblrdemo.theme.TumblrDemoTheme

@Composable
fun PostsScreen(
    drawerState: DrawerState,
    posts: List<PostEntity>?,
    onFavoriteClick: (postId: Long?) -> Unit,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onNavigate: Navigate
) {
    Scaffold(
        topBar = {
            AppTopBar(
                topBarTitle = stringResource(id = R.string.posts),
                drawerState = drawerState,
                navigationIconType = NavigationIconType.DRAWER_ICON
            )
        }
    ) { padding ->
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
                        paddingValues = padding,
                        onNavigate = onNavigate
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PostsScreenPreview() {
    TumblrDemoTheme {
        PostsScreen(
            drawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
            posts = MockData().postEntities,
            onNavigate = {},
            isRefreshing = false,
            onRefresh = {},
            onFavoriteClick = {}
        )
    }
}
