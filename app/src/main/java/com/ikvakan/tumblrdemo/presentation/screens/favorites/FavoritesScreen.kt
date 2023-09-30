package com.ikvakan.tumblrdemo.presentation.screens.favorites

import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.ikvakan.tumblrdemo.R
import com.ikvakan.tumblrdemo.presentation.screens.composables.AppTopBar
import com.ikvakan.tumblrdemo.presentation.screens.composables.NavigationIconType

@Composable
fun FavoritesScreen(drawerState: DrawerState) {
    Scaffold(
        topBar = {
            AppTopBar(
                topBarTitle = stringResource(id = R.string.favorites ),
                drawerState = drawerState,
                navigationIconType = NavigationIconType.DRAWER_ICON
            )
        }
    ) { padding ->
    }
}
