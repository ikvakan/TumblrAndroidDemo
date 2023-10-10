package com.ikvakan.tumblrdemo.presentation.screens.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.ikvakan.tumblrdemo.R
import com.ikvakan.tumblrdemo.data.mock.MockData
import com.ikvakan.tumblrdemo.domain.model.Post
import com.ikvakan.tumblrdemo.presentation.navigation.Navigate
import com.ikvakan.tumblrdemo.theme.TumblrDemoTheme

@Composable
fun PostListContent(
    modifier: Modifier = Modifier,
    posts: List<Post>,
    isLoadingMorePosts: Boolean = false,
    onFavoriteClick: (postId: Long?) -> Unit,
    onDeletePost: (Long?) -> Unit,
    onLoadMoreItems: () -> Unit = {},
    onNavigate: Navigate
) {
    val lazyListState = rememberLazyListState()

    val firsVisibleIndex by remember { derivedStateOf { lazyListState.firstVisibleItemIndex } }

    val lastVisibleIndex by remember {
        derivedStateOf {
            lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
        }
    }

    if (posts.isNotEmpty() && (firsVisibleIndex + lastVisibleIndex) >= posts.size - 1) {
        onLoadMoreItems()
    }
    LazyColumn(
        modifier = modifier.padding(vertical = dimensionResource(id = R.dimen.small_padding)),
        state = lazyListState,
        content = {
            items(posts.size, key = { it }) { index ->

                AppCard(
                    post = posts[index],
                    onFavoriteClick = onFavoriteClick,
                    onDeletePost = onDeletePost,
                    onNavigate = onNavigate
                )
            }
            if (isLoadingMorePosts) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PostListContentPreview() {
    TumblrDemoTheme {
        PostListContent(
            posts = MockData().postEntities,
            onFavoriteClick = {},
            onLoadMoreItems = {},
            onDeletePost = {},
            isLoadingMorePosts = false,
            onNavigate = {}
        )
    }
}
