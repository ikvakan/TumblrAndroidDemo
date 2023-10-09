package com.ikvakan.tumblrdemo.presentation.screens.posts

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ikvakan.tumblrdemo.data.mock.MockData
import com.ikvakan.tumblrdemo.domain.model.Post
import com.ikvakan.tumblrdemo.presentation.navigation.Navigate
import com.ikvakan.tumblrdemo.presentation.screens.composables.PostListContent
import com.ikvakan.tumblrdemo.theme.TumblrDemoTheme

@Composable
fun FavoritesScreen(
    paddingValues: PaddingValues,
    posts: List<Post>?,
    onFavoriteClick: (Long?) -> Unit,
    onDeletePost: (Long?) -> Unit,
    onNavigate: Navigate
) {
    if (posts != null) {
        PostListContent(
            posts = posts,
            onFavoriteClick = onFavoriteClick,
            paddingValues = paddingValues,
            onDeletePost = onDeletePost,
            onNavigate = onNavigate
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FavoritesScreenPreview() {
    TumblrDemoTheme {
        FavoritesScreen(
            paddingValues = PaddingValues(),
            posts = MockData().postEntities,
            onFavoriteClick = {},
            onDeletePost = {},
            onNavigate = {}
        )
    }
}
