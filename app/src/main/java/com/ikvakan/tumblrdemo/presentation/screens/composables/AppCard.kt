package com.ikvakan.tumblrdemo.presentation.screens.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.ikvakan.tumblrdemo.R
import com.ikvakan.tumblrdemo.data.mock.MockData
import com.ikvakan.tumblrdemo.domain.model.PostEntity
import com.ikvakan.tumblrdemo.presentation.navigation.AppScreen
import com.ikvakan.tumblrdemo.presentation.navigation.Navigate
import com.ikvakan.tumblrdemo.theme.TumblrDemoTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    post: PostEntity,
    onNavigate: Navigate,
    onFavoriteClick: (postId: Long?) -> Unit
) {
    Card(
        onClick = { onNavigate(AppScreen.PostDetailsScreen(postId = post.id.toString())) },
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.small_padding)),
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(id = R.dimen.default_elevation)),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.small_padding)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            GlideImage(
                model = post.imageUrl,
                contentDescription = null,
                loading = placeholder(R.drawable.placeholder),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.small_padding))
                    .size(dimensionResource(id = R.dimen.default_image_size))
                    .clip(CircleShape)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentHeight()
            ) {
                Text(
                    text = stringResource(id = R.string.blog_title, post.blogTitle)
                )
                Text(
                    text = stringResource(id = R.string.post_summary, post.summary),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
            }
            FavoriteIconButton(
                onFavoriteClick = onFavoriteClick,
                isFavorite = post.isFavorite,
                postId = post.id
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppCardPreview() {
    TumblrDemoTheme {
        AppCard(
            post = MockData().postEntities[0],
            onNavigate = {},
            onFavoriteClick = {}
        )
    }
}
