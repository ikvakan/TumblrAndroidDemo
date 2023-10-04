package com.ikvakan.tumblrdemo.presentation.screens.composables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ikvakan.tumblrdemo.data.mock.MockData
import com.ikvakan.tumblrdemo.domain.model.PostEntity
import com.ikvakan.tumblrdemo.presentation.navigation.Navigate
import com.ikvakan.tumblrdemo.theme.TumblrDemoTheme

@Composable
fun PostListContent(
    modifier: Modifier = Modifier,
    posts: List<PostEntity>,
    onFavoriteClick: (postId: Long?) -> Unit,
    paddingValues: PaddingValues,
    onNavigate: Navigate
) {
    LazyColumn(modifier = modifier.padding(paddingValues), content = {
        items(posts) { post ->
            AppCard(
                post = post,
                onFavoriteClick = onFavoriteClick,
                onNavigate = onNavigate
            )
        }
    })
}

@Preview(showBackground = true)
@Composable
fun PostListContentPreview() {
    TumblrDemoTheme {
        PostListContent(
            posts = MockData().postEntities,
            onFavoriteClick = {},
            paddingValues = PaddingValues(),
            onNavigate = {}
        )
    }
}
