package com.ikvakan.tumblrdemo.presentation.screens.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.ikvakan.tumblrdemo.R
import com.ikvakan.tumblrdemo.data.mock.MockData
import com.ikvakan.tumblrdemo.domain.model.Post
import com.ikvakan.tumblrdemo.theme.TumblrDemoTheme

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DetailsScreenContent(
    modifier: Modifier = Modifier,
    post: Post,
    paddingValues: PaddingValues,
    onFavoriteClick: (Long?) -> Unit,
    imageSize: Dp = dimensionResource(id = R.dimen.large_image_size),
    iconSize: Dp = dimensionResource(id = R.dimen.large_icon_size)
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_padding))
    ) {
        FavoriteIconRow(
            onFavoriteClick = onFavoriteClick,
            isFavorite = post.isFavorite,
            iconSize = iconSize,
            postId = post.id
        )
        GlideImage(
            model = post.imageUrl,
            contentDescription = null,
            loading = placeholder(R.drawable.placeholder),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(horizontal = dimensionResource(id = R.dimen.small_padding))
                .size(imageSize)
                .clip(CircleShape)
        )
        Text(text = stringResource(id = R.string.blog), fontWeight = FontWeight.Bold)
        Text(text = post.blogTitle, textAlign = TextAlign.Center)
        Text(text = stringResource(id = R.string.summary), fontWeight = FontWeight.Bold)
        Text(text = post.summary, textAlign = TextAlign.Justify)
    }
}

@Preview(showBackground = true)
@Composable
fun DetailsScreenContentPreview() {
    TumblrDemoTheme {
        DetailsScreenContent(
            post = MockData().postEntities[0],
            onFavoriteClick = {},
            paddingValues = PaddingValues()
        )
    }
}
