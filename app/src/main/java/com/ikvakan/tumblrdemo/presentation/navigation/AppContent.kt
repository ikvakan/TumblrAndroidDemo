package com.ikvakan.tumblrdemo.presentation.navigation

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
import timber.log.Timber

typealias Navigate = (screen: AppScreen) -> Unit
typealias OnBack = () -> Unit

@Composable
fun AppContent() {
    val navController = rememberNavController()
    val onNavigate: Navigate = { scree ->
        Timber.d("navigate to: ${scree.route}")
        navController.navigate(scree.route) {
            if (scree.clearBackstack) {
                popUpTo(0)
            }
        }
    }
    val onBack: OnBack = { navController.popBackStack() }

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
                // view model extract
                PostsScreen()
            }
        }
    }
}
