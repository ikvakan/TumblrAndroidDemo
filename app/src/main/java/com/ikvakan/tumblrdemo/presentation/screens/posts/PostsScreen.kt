package com.ikvakan.tumblrdemo.presentation.screens.posts

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ikvakan.tumblrdemo.domain.model.PostEntity

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostsScreen(
    modifier: Modifier = Modifier,
    posts: List<PostEntity>
) {
    Scaffold(
        modifier = modifier,
        topBar = { TopAppBar(title = { Text(text = "Posts") }) }

    ) { padding ->
    }
}
