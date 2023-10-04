package com.ikvakan.tumblrdemo.presentation.navigation.drawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ikvakan.tumblrdemo.R
import com.ikvakan.tumblrdemo.presentation.navigation.AppScreen
import com.ikvakan.tumblrdemo.presentation.navigation.Navigate
import com.ikvakan.tumblrdemo.theme.TumblrDemoTheme

@Composable
fun DrawerContent(
    route: String,
    modifier: Modifier = Modifier,
    onNavigate: Navigate,
    onClick: () -> Unit
) {
    ModalDrawerSheet(modifier = modifier, drawerContainerColor = MaterialTheme.colorScheme.background) {
        DrawerHeader()
        Column(
            Modifier.padding(dimensionResource(id = R.dimen.medium_padding)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_padding))
        ) {
            NavigationDrawerItem(
                label = { Text(text = stringResource(id = R.string.posts)) },
                selected = route == AppScreen.PostsScreen.route,
                onClick =
                {
                    onNavigate.invoke(AppScreen.PostsScreen())
                    onClick()
                },
                icon = { Icon(imageVector = Icons.Default.Home, contentDescription = null) },
                shape = MaterialTheme.shapes.small
            )

            NavigationDrawerItem(
                label = { Text(text = stringResource(id = R.string.favorites)) },
                selected = route == AppScreen.FavoritesScreen.route,
                onClick =
                {
                    onNavigate.invoke(AppScreen.FavoritesScreen())
                    onClick()
                },
                icon = { Icon(imageVector = Icons.Default.Favorite, contentDescription = null) },
                shape = MaterialTheme.shapes.small
            )
        }
    }
}

@Composable
fun DrawerHeader(modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth()) {
        Image(
            painterResource(id = R.drawable.marvel_logo),
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.medium_padding)),
            contentDescription = null
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DrawerHeaderPreview() {
    TumblrDemoTheme {
        DrawerHeader()
    }
}

@Preview
@Composable
fun DrawerContentPreview() {
    TumblrDemoTheme {
        DrawerContent(
            route = AppScreen.PostsScreen.route,
            onNavigate = {},
            onClick = {}
        )
    }
}
