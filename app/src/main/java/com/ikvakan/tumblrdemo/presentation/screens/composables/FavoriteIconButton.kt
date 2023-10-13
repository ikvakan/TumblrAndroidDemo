package com.ikvakan.tumblrdemo.presentation.screens.composables

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.ikvakan.tumblrdemo.R
import com.ikvakan.tumblrdemo.theme.TumblrDemoTheme

@Composable
fun FavoriteIconButton(
    modifier: Modifier = Modifier,
    onFavoriteClick: (Long?) -> Unit,
    isFavorite: Boolean,
    iconSize: Dp = dimensionResource(id = R.dimen.default_icon_size),
    postId: Long?
) {
    val favoriteIcon = if (isFavorite) {
        Icons.Default.Favorite
    } else {
        Icons.Default.FavoriteBorder
    }

    IconButton(onClick = { onFavoriteClick(postId) }) {
        Icon(
            imageVector = favoriteIcon,
            contentDescription = null,
            modifier = modifier
                .size(iconSize)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FavoriteIconButtonPreview() {
    TumblrDemoTheme {
        FavoriteIconButton(
            onFavoriteClick = {},
            isFavorite = false,
            postId = null
        )
    }
}
