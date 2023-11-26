package com.ikvakan.tumblrdemo.presentation.screens.posts

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ikvakan.tumblrdemo.data.mock.MockData
import com.ikvakan.tumblrdemo.domain.model.Post
import com.ikvakan.tumblrdemo.presentation.Navigate
import com.ikvakan.tumblrdemo.presentation.screens.composables.FavoriteListContent
import com.ikvakan.tumblrdemo.theme.TumblrDemoTheme

@Composable
fun FavoritesScreen(
    posts: List<Post>?,
    onFavoriteClick: (Post?) -> Unit,
    onDeletePost: (Long?) -> Unit,
    onNavigate: Navigate
) {
    if (posts != null) {
        FavoriteListContent(
            posts = posts,
            onFavoriteClick = onFavoriteClick,
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
            posts = MockData().postEntities,
            onFavoriteClick = {},
            onDeletePost = {},
            onNavigate = {}
        )
    }
}
