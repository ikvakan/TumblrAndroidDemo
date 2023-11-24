package com.ikvakan.tumblrdemo.presentation.screens.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
    postsFlow: LazyPagingItems<Post>?,
    // posts: List<Post>,
    isLoadingMorePosts: Boolean = false,
//    onFavoriteClick: (postId: Long?) -> Unit,
    onFavoriteClick: (Post?) -> Unit,
    onDeletePost: (Long?) -> Unit,
    onNavigate: Navigate
) {
    val lazyListState = rememberLazyListState()

    LazyColumn(
        modifier = modifier.padding(vertical = dimensionResource(id = R.dimen.small_padding)),
        state = lazyListState,
        content = {
            if (postsFlow != null) {
                items(
                    count = postsFlow.itemCount,
                    key = postsFlow.itemKey { it.postId!! }
                ) { index ->
                    val post = postsFlow[index]
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
            postsFlow = null,
            // posts = MockData().postEntities,
            onFavoriteClick = {},
            onDeletePost = {},
            isLoadingMorePosts = false,
            onNavigate = {}
        )
    }
}
