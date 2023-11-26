package com.ikvakan.tumblrdemo.presentation.screens.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.ikvakan.tumblrdemo.R
import com.ikvakan.tumblrdemo.domain.model.Post
import com.ikvakan.tumblrdemo.presentation.Navigate
import com.ikvakan.tumblrdemo.theme.TumblrDemoTheme

@Composable
fun PostListContent(
    modifier: Modifier = Modifier,
    posts: LazyPagingItems<Post>?,
    isLoadingMorePosts: Boolean = false,
    onFavoriteClick: (Post?) -> Unit,
    onDeletePost: (Long?) -> Unit,
    onNavigate: Navigate
) {
    val lazyListState = rememberLazyListState()

    LazyColumn(
        modifier = modifier.padding(vertical = dimensionResource(id = R.dimen.small_padding)),
        state = lazyListState,
        content = {
            if (posts != null) {
                items(
                    count = posts.itemCount,
                    key = posts.itemKey { it.postId!! }
                ) { index ->
                    val post = posts[index]
                    if (post != null) {
                        AppCard(
                            post = post,
                            onFavoriteClick = onFavoriteClick,
                            onDeletePost = onDeletePost,
                            onNavigate = onNavigate
                        )
                    }
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
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PostListContentPreview() {
    TumblrDemoTheme {
        PostListContent(
            posts = null,
            onFavoriteClick = {},
            onDeletePost = {},
            isLoadingMorePosts = false,
            onNavigate = {}
        )
    }
}
