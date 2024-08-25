package com.example.bottomnavbarapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavbarItem(val route: String, val icon: ImageVector) {

    data object Notes : NavbarItem("Notes", Icons.Default.Home)
    data object Categories : NavbarItem("Categories", Icons.Default.List)
    data object Favorites : NavbarItem("Favorites", Icons.Default.Favorite)
    data object Settings : NavbarItem("Settings", Icons.Default.Settings)

    companion object {
        val routes = listOf(Notes, Categories, Favorites, Settings)
    }
}