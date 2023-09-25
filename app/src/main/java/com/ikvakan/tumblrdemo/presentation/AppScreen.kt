package com.ikvakan.tumblrdemo.presentation

sealed class AppScreen {
    abstract val route: String
    var clearBackstack = false

    fun isSame(other: AppScreen): Boolean {
        return javaClass == other.javaClass
    }

    override fun toString(): String {
        return "${javaClass.simpleName}(route='$route', clearBackstack = $clearBackstack)"
    }
    class PostsScreen : AppScreen() {
        companion object {
            const val routDef = "postsScreen"
        }

        init {
            clearBackstack = true
        }
        override val route: String
            get() = routDef
    }
}
