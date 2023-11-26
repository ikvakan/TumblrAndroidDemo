package com.ikvakan.tumblrdemo.presentation

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.ikvakan.tumblrdemo.R
import com.ikvakan.tumblrdemo.core.BaseAppScreen
import com.ikvakan.tumblrdemo.presentation.navigation.drawer.AppDrawer
import com.ikvakan.tumblrdemo.presentation.screens.composables.AppTopBar
import com.ikvakan.tumblrdemo.presentation.screens.composables.NavigationIconType
import com.ikvakan.tumblrdemo.presentation.screens.posts.FavoritesScreen
import com.ikvakan.tumblrdemo.presentation.screens.posts.PostDetailsScreen
import com.ikvakan.tumblrdemo.presentation.screens.posts.PostsScreen
import com.ikvakan.tumblrdemo.presentation.screens.posts.PostsViewModel
import com.ikvakan.tumblrdemo.theme.TumblrDemoTheme
import com.ikvakan.tumblrdemo.util.extensions.getException
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import timber.log.Timber

typealias Navigate = (screen: AppScreen) -> Unit
typealias OnBack = () -> Unit

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun AppContent(
    postsViewModel: PostsViewModel = getViewModel()
) {
    val uiState by postsViewModel.uiState.collectAsStateWithLifecycle()
    val posts = uiState.postsFlow?.collectAsLazyPagingItems()

    val navController = rememberNavController()
    val onNavigate: Navigate = { screen ->
        Timber.d("navigate to: ${screen.routeDef}")
        navController.navigate(screen.routeDef) {
            if (screen.clearBackstack) {
                popUpTo(0)
            }
        }
    }
    val onBack: OnBack = { navController.popBackStack() }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: AppScreen.PostsScreen.route

    TumblrDemoTheme {
        AppDrawer(
            currentRoute = currentRoute,
            onNavigate = onNavigate,
            onClick = {
                coroutineScope.launch { if (drawerState.isOpen) drawerState.close() else drawerState.open() }
            },
            drawerState = drawerState
        ) {
            NavHost(
                navController = navController,
                startDestination = AppScreen.PostsScreen.route,
                enterTransition = { scaleIn(initialScale = 0.7f) + fadeIn() },
                exitTransition = { scaleOut(targetScale = 1.3f) + fadeOut() },
                popEnterTransition = { scaleIn(initialScale = 1.3f) + fadeIn() },
                popExitTransition = { scaleOut(targetScale = 0.7f) + fadeOut() }
            ) {
                composable(
                    route = AppScreen.PostsScreen.route,
                ) {
                    BaseAppScreen(
                        viewModel = postsViewModel,
                        progress = posts?.loadState?.refresh is LoadState.Loading,
                        onNavigate = onNavigate,
                        coroutineScope = coroutineScope,
                        exception = posts.getException(),
                        topBar = {
                            AppTopBar(
                                topBarTitle = stringResource(id = R.string.posts),
                                drawerState = drawerState,
                                navigationIconType = NavigationIconType.DRAWER_ICON
                            )
                        }
                    ) {
                        PostsScreen(
                            posts = posts,
                            isLoadingMorePosts = posts?.loadState?.append is LoadState.Loading,
                            onFavoriteClick = { post -> postsViewModel.toggleIsFavoritePost(post) },
                            isRefreshing = uiState.isRefreshing,
                            onRefresh = {
                                posts?.refresh()
                                postsViewModel.toggleIsRefreshing(posts?.loadState?.refresh is LoadState.Loading)
                            },
                            onDeletePost = { postId -> postsViewModel.onDeletePost(postId) },
                            onNavigate = onNavigate
                        )
                    }
                }
                composable(
                    route = AppScreen.PostDetailsScreen.route,
                    arguments = AppScreen.PostDetailsScreen.arguments
                ) { backStackEntry ->
                    val postId =
                        AppScreen.PostDetailsScreen.getPostId(backStackEntry)?.toLongOrNull()
                    postsViewModel.setSelectedPost(postId)

                    BaseAppScreen(
                        viewModel = postsViewModel,
                        onNavigate = onNavigate,
                        progress = uiState.progress,
                        coroutineScope = coroutineScope,
                        topBar = {
                            AppTopBar(
                                topBarTitle = stringResource(id = R.string.post_details),
                                navigationIconType = NavigationIconType.BACK_ICON,
                                onBack = onBack
                            )
                        },
                    ) {
                        PostDetailsScreen(
                            post = uiState.selectedPost,
                            onFavoriteClick = { post -> postsViewModel.toggleIsFavoritePost(post) }
                        )
                    }
                }
                composable(route = AppScreen.FavoritesScreen.route) {
                    BaseAppScreen(
                        viewModel = postsViewModel,
                        onNavigate = onNavigate,
                        coroutineScope = coroutineScope,
                        topBar = {
                            AppTopBar(
                                topBarTitle = stringResource(id = R.string.favorites),
                                drawerState = drawerState,
                                navigationIconType = NavigationIconType.DRAWER_ICON
                            )
                        }
                    ) {
                        FavoritesScreen(
                            posts = uiState.favoritePosts,
                            onDeletePost = { postId -> postsViewModel.onDeletePost(postId) },
                            onFavoriteClick = { post -> postsViewModel.toggleIsFavoritePost(post) },
                            onNavigate = onNavigate
                        )
                    }
                }
            }
        }
    }
}
