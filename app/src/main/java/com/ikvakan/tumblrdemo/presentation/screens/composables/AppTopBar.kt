package com.ikvakan.tumblrdemo.presentation.screens.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.ikvakan.tumblrdemo.R
import com.ikvakan.tumblrdemo.presentation.OnBack
import com.ikvakan.tumblrdemo.theme.TumblrDemoTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    topBarTitle: String,
    drawerState: DrawerState? = null,
    onBack: OnBack = {},
    navigationIconType: NavigationIconType
) {
    Surface(shadowElevation = dimensionResource(id = R.dimen.default_elevation)) {
        CenterAlignedTopAppBar(
            title = { Text(text = topBarTitle) },
            modifier = modifier.fillMaxWidth(),
            navigationIcon = {
                when (navigationIconType) {
                    NavigationIconType.DRAWER_ICON -> {
                        DrawerMenuNavigationIcon(drawerState = drawerState)
                    }

                    NavigationIconType.BACK_ICON -> {
                        DrawerBackNavigationIcon(onBack = onBack)
                    }
                }
            }
        )
    }
}

@Composable
fun DrawerMenuNavigationIcon(drawerState: DrawerState?) {
    val coroutineScope = rememberCoroutineScope()
    IconButton(
        onClick = { coroutineScope.launch { drawerState?.open() } },
        content = {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = null
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DrawerMenuNavigationIconPreview() {
    TumblrDemoTheme {
        DrawerMenuNavigationIcon(drawerState = null)
    }
}

@Composable
fun DrawerBackNavigationIcon(onBack: OnBack) {
    IconButton(
        onClick = onBack,
        content = {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DrawerBackNavigationIconPreview() {
    TumblrDemoTheme {
        DrawerBackNavigationIcon(onBack = {})
    }
}

@Preview(showBackground = true)
@Composable
fun AppTopBarPreview() {
    TumblrDemoTheme {
        AppTopBar(
            topBarTitle = "Favorites",
            navigationIconType = NavigationIconType.DRAWER_ICON
        )
    }
}
enum class NavigationIconType {
    DRAWER_ICON,
    BACK_ICON
}
