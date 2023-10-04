package com.ikvakan.tumblrdemo.presentation.screens.posts

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ikvakan.tumblrdemo.R
import com.ikvakan.tumblrdemo.data.mock.MockData
import com.ikvakan.tumblrdemo.domain.model.PostEntity
import com.ikvakan.tumblrdemo.presentation.navigation.Navigate
import com.ikvakan.tumblrdemo.presentation.screens.composables.AppTopBar
import com.ikvakan.tumblrdemo.presentation.screens.composables.NavigationIconType
import com.ikvakan.tumblrdemo.presentation.screens.composables.PostListContent
import com.ikvakan.tumblrdemo.theme.TumblrDemoTheme

@Composable
fun FavoritesScreen(
    posts: List<PostEntity>?,
    onFavoriteClick: (Long?) -> Unit,
    onNavigate: Navigate,
    drawerState: DrawerState
) {
    Scaffold(
        topBar = {
            AppTopBar(
                topBarTitle = stringResource(id = R.string.favorites),
                drawerState = drawerState,
                navigationIconType = NavigationIconType.DRAWER_ICON
            )
        }
    ) { padding ->
        if (posts != null) {
            PostListContent(
                posts = posts,
                onFavoriteClick = onFavoriteClick,
                paddingValues = padding,
                onNavigate = onNavigate
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavoritesScreenPreview() {
    TumblrDemoTheme {
        FavoritesScreen(
            posts = MockData().postEntities,
            onFavoriteClick = {},
            onNavigate = {},
            drawerState = rememberDrawerState(
                initialValue = DrawerValue.Closed
            )
        )
    }
}
