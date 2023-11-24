package com.ikvakan.tumblrdemo.presentation.screens.posts

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ikvakan.tumblrdemo.data.mock.MockData
import com.ikvakan.tumblrdemo.domain.model.Post
import com.ikvakan.tumblrdemo.presentation.screens.composables.DetailsScreenContent
import com.ikvakan.tumblrdemo.theme.TumblrDemoTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PostDetailsScreen(
    post: Post?,
//    onFavoriteClick: (Long?) -> Unit,
    onFavoriteClick: (Post?) -> Unit,
) {
    if (post != null) {
        DetailsScreenContent(
            post = post,
            onFavoriteClick = onFavoriteClick,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PostDetailsScreenPreview() {
    TumblrDemoTheme {
        PostDetailsScreen(
            post = MockData().postEntities[0],
            onFavoriteClick = {},
        )
    }
}
