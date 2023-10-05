package com.ikvakan.tumblrdemo.presentation.screens.posts

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ikvakan.tumblrdemo.R
import com.ikvakan.tumblrdemo.data.mock.MockData
import com.ikvakan.tumblrdemo.domain.model.Post
import com.ikvakan.tumblrdemo.presentation.navigation.OnBack
import com.ikvakan.tumblrdemo.presentation.screens.composables.AppTopBar
import com.ikvakan.tumblrdemo.presentation.screens.composables.DetailsScreenContent
import com.ikvakan.tumblrdemo.presentation.screens.composables.NavigationIconType
import com.ikvakan.tumblrdemo.theme.TumblrDemoTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PostDetailsScreen(
    post: Post?,
    onFavoriteClick: (Long?) -> Unit,
    onBack: OnBack
) {
    Scaffold(
        topBar = {
            AppTopBar(
                topBarTitle = stringResource(id = R.string.post_details),
                navigationIconType = NavigationIconType.BACK_ICON,
                onBack = onBack
            )
        }
    ) { padding ->
        if (post != null) {
            DetailsScreenContent(
                post = post,
                onFavoriteClick = onFavoriteClick,
                paddingValues = padding
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PostDetailsScreenPreview() {
    TumblrDemoTheme {
        PostDetailsScreen(
            post = MockData().postEntities[0],
            onFavoriteClick = {},
            onBack = {}
        )
    }
}
