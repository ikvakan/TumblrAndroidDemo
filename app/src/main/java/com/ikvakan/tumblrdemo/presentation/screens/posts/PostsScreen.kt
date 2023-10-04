package com.ikvakan.tumblrdemo.presentation.screens.posts

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ikvakan.tumblrdemo.R
import com.ikvakan.tumblrdemo.data.mock.MockData
import com.ikvakan.tumblrdemo.domain.model.PostEntity
import com.ikvakan.tumblrdemo.presentation.navigation.Navigate
import com.ikvakan.tumblrdemo.presentation.screens.composables.AppCard
import com.ikvakan.tumblrdemo.presentation.screens.composables.AppTopBar
import com.ikvakan.tumblrdemo.presentation.screens.composables.NavigationIconType
import com.ikvakan.tumblrdemo.theme.TumblrDemoTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PostsScreen(
    drawerState: DrawerState,
    posts: List<PostEntity>?,
    onFavoriteClick: (postId: Long?) -> Unit,
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
            LazyColumn(modifier = Modifier.padding(padding), content = {
                items(posts) { post ->
                    AppCard(
                        post = post,
                        onFavoriteClick = onFavoriteClick,
                        onNavigate = onNavigate
                    )
                }
            })
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
            onFavoriteClick = {}
        )
    }
}
