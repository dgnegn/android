package com.example.bottomnavbarapp


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import androidx.compose.foundation.layout.Arrangement as Arrangement


@Composable
fun MainScreen() {

    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(navController)
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = NavHostGraphRoute.NotesGraph.route,
            modifier = Modifier.padding(it)
        ) {
            notesGraph(navController)
            notesDetailGraph(navController)
        }
    }

}
@Composable
fun NavigationBar(navController: NavHostController) {

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination
    val isVisible=NavbarItem.routes.any{it.route==currentDestination?.route}

    if (isVisible) {
        NavigationBar {
            NavbarItem.routes.forEach { navbarItem ->
                NavigationBarItem(
                    selected = currentDestination?.hierarchy?.any { it.route == navbarItem.route } == true,
                    onClick = {
                        navController.navigate(navbarItem.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = navbarItem.icon,
                            contentDescription = navbarItem.route
                        )
                    },
                    label = {
                        Text(text = navbarItem.route)
                    }
                )
            }
        }
    }
}

private fun NavGraphBuilder.notesGraph(navController: NavHostController) {
    navigation(
        route = NavHostGraphRoute.NotesGraph.route,
        startDestination = NavbarItem.Notes.route
    )
    {
        composable(
            NavbarItem.Notes.route
        ) {
            NotesScreen(
                navigateToNoteDetail = { id: String ->
                    navController.navigate(
                        NavHostGraphRoute.NotesDetail.route + "/{$id}"
                    )
                }
            )
        }
        composable(NavbarItem.Categories.route) {
            CategoriesScreen()
        }
        composable(NavbarItem.Favorites.route) {
            FavoritesScreen()
        }
        composable(NavbarItem.Settings.route) {
            SettingsScreen()
        }
    }
}

private fun NavGraphBuilder.notesDetailGraph(navController: NavHostController) {
    navigation(
        route = NavHostGraphRoute.NotesDetailGraph.route,
        startDestination = NavHostGraphRoute.NotesDetail.route,

        ) {
        composable(
            route = NavHostGraphRoute.NotesDetail.route + "/{id}",
            arguments = listOf(navArgument("id") {
                type = NavType.StringType
            })
        ) {
            val id: String = it.arguments?.getString("id").toString()
            NoteDetailScreen(id = id)
        }
    }
}

@Composable
fun NoteDetailScreen(
    id: String
) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Note Detail and args:$id")
    }
}

@Composable
fun SettingsScreen() {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Settings")
    }
}

@Composable
fun FavoritesScreen() {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Favorites")
    }
}

@Composable
fun CategoriesScreen() {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Categories")
    }
}

@Composable
fun NotesScreen(
    navigateToNoteDetail: (id: String) -> Unit
) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
       Button(onClick = { navigateToNoteDetail("1234")}) {
           Text(text = "Go to Note Detail")
       }


    }
}
