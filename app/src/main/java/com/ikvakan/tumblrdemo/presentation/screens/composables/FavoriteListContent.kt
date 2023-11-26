package com.ikvakan.tumblrdemo.presentation.screens.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.ikvakan.tumblrdemo.R
import com.ikvakan.tumblrdemo.data.mock.MockData
import com.ikvakan.tumblrdemo.domain.model.Post
import com.ikvakan.tumblrdemo.presentation.Navigate
import com.ikvakan.tumblrdemo.theme.TumblrDemoTheme

@Composable
fun FavoriteListContent(
    modifier: Modifier = Modifier,
    posts: List<Post>,
    onFavoriteClick: (Post?) -> Unit,
    onDeletePost: (Long?) -> Unit,
    onNavigate: Navigate
) {
    val lazyListState = rememberLazyListState()

    LazyColumn(
        modifier = modifier.padding(vertical = dimensionResource(id = R.dimen.small_padding)),
        state = lazyListState,
        content = {
            items(
                items = posts
            ) {
                AppCard(
                    post = it,
                    onFavoriteClick = onFavoriteClick,
                    onDeletePost = onDeletePost,
                    onNavigate = onNavigate
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun FavoritesListContentPreview() {
    TumblrDemoTheme {
        FavoriteListContent(
            posts = MockData().postEntities,
            onFavoriteClick = {},
            onDeletePost = {},
            onNavigate = {}
        )
    }
}
