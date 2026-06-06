package com.example.bookstoreapp.ui.bottom_menu

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource

@Composable
fun BottomMenu(
    selectedScreen: BottomScreen,
    onItemClick: (BottomScreen) -> Unit
) {
    val items = listOf(
        BottomMenuItem.Home,
        BottomMenuItem.Favs,
        BottomMenuItem.Cart,
        BottomMenuItem.Settings
    )

    NavigationBar {
        items.forEach { item ->

            val screen = when (item) {
                BottomMenuItem.Home -> BottomScreen.Books
                BottomMenuItem.Favs -> BottomScreen.Favorites
                BottomMenuItem.Settings -> BottomScreen.Profile
                BottomMenuItem.Cart -> BottomScreen.Cart
            }

            NavigationBarItem(
                selected = selectedScreen == screen,
                onClick = {
                    onItemClick(screen)
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconId),
                        contentDescription = null
                    )
                },
                label = {
                    Text(text = item.title)
                }
            )
        }
    }
}

