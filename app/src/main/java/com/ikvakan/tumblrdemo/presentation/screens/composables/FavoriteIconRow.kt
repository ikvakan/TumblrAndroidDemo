package com.ikvakan.tumblrdemo.presentation.screens.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.ikvakan.tumblrdemo.R
import com.ikvakan.tumblrdemo.domain.model.Post
import com.ikvakan.tumblrdemo.theme.TumblrDemoTheme

@Composable
fun FavoriteIconRow(
    modifier: Modifier = Modifier,
//    onFavoriteClick: (Long?) -> Unit,
    onFavoriteClick: (Post?) -> Unit,
    isFavorite: Boolean,
    post:Post,
    iconSize: Dp,
    postId: Long?
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        FavoriteIconButton(
            onFavoriteClick = onFavoriteClick,
            isFavorite = isFavorite,
            iconSize = iconSize,
            post = post,
            postId = postId
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FavoriteIconRowPreview() {
    TumblrDemoTheme {
        FavoriteIconRow(
            onFavoriteClick = {},
            isFavorite = false,
            post= Post(),
            iconSize = dimensionResource(id = R.dimen.large_icon_size),
            postId = Long.MIN_VALUE
        )
    }
}
