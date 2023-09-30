package com.ikvakan.tumblrdemo.presentation.screens.drawer

import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.runtime.Composable
import com.ikvakan.tumblrdemo.presentation.navigation.Navigate

@Composable
fun AppDrawer(
    drawerState: DrawerState,
    onNavigate: Navigate,
    currentRoute: String,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    ModalNavigationDrawer(
        drawerContent = {
            DrawerContent(
                route = currentRoute,
                onNavigate = onNavigate,
                onClick = onClick
            )
        },
        drawerState = drawerState,
        content = content
    )
}
