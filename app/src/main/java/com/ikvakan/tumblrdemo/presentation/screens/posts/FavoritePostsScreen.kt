package com.ikvakan.tumblrdemo.presentation.screens.posts

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ikvakan.tumblrdemo.data.mock.MockData
import com.ikvakan.tumblrdemo.domain.model.Post
import com.ikvakan.tumblrdemo.presentation.navigation.Navigate
import com.ikvakan.tumblrdemo.presentation.screens.composables.PostListContent
import com.ikvakan.tumblrdemo.theme.TumblrDemoTheme

@Composable
fun FavoritesScreen(
    favoritePosts: List<Post>?,
    onFavoriteClick: (Long?) -> Unit,
    onDeletePost: (Long?) -> Unit,
    onNavigate: Navigate
) {
    if (favoritePosts != null) {
        PostListContent(
            posts = favoritePosts,
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
            favoritePosts = MockData().postEntities,
            onFavoriteClick = {},
            onDeletePost = {},
            onNavigate = {}
        )
    }
}
