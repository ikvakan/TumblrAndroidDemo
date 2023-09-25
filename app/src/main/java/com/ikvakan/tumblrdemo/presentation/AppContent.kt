package com.ikvakan.tumblrdemo.presentation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ikvakan.tumblrdemo.presentation.screens.PostsScreen
import com.ikvakan.tumblrdemo.theme.TumblrDemoTheme

@Composable
fun AppContent() {
    val navController = rememberNavController()

    TumblrDemoTheme {
        NavHost(
            navController = navController,
            startDestination = AppScreen.PostsScreen.routDef,
            enterTransition = { scaleIn(initialScale = 0.7f) + fadeIn() },
            exitTransition = { scaleOut(targetScale = 1.3f) + fadeOut() },
            popEnterTransition = { scaleIn(initialScale = 1.3f) + fadeIn() },
            popExitTransition = { scaleOut(targetScale = 0.7f) + fadeOut() }
        ) {
            composable(
                route = AppScreen.PostsScreen.routDef,
            ) {
                PostsScreen()
            }
        }
    }
}
