package com.ikvakan.tumblrdemo.presentation.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument

/**
 * Class that defines app routes and provides navigation parameters to other composables in the [AppContent] composable
 */

sealed class AppScreen {
    abstract val routeDef: String
    var clearBackstack = false

    fun isSame(other: AppScreen): Boolean {
        return javaClass == other.javaClass
    }

    override fun toString(): String {
        return "${javaClass.simpleName}(route='$routeDef', clearBackstack = $clearBackstack)"
    }
    class PostsScreen : AppScreen() {
        companion object {
            const val route = "postsScreen"
        }

        init {
            clearBackstack = true
        }
        override val routeDef: String
            get() = route
    }
    class PostDetailsScreen(private val postId: String) : AppScreen() {
        companion object {
            const val route = "postsDetailsScreen?postId={postId}"
            val arguments = listOf(
                navArgument("postId") {
                    type = NavType.StringType
                    nullable = true
                }
            )

            fun getPostId(backStackEntry: NavBackStackEntry) = backStackEntry.arguments?.getString("postId")
        }
        override val routeDef: String
            get() = "postsDetailsScreen?postId=$postId"
    }
    class FavoritesScreen : AppScreen() {
        companion object {
            const val route = "favoritesScreen"
        }

        init {
            clearBackstack = true
        }
        override val routeDef: String
            get() = route
    }
}
