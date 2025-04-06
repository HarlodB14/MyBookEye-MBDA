package com.example.mybookeye.Nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mybookeye.Controller.NavController
import BookDetailScreen
import BookListScreen
import SettingsScreen
import com.example.mybookeye.Screens.FavoritesScreen

@Composable
fun Navigation(navController: NavController) {
    NavHost(
        navController = navController.getAndroidXNavController(),
        startDestination = "booklist"
    ) {
        composable("booklist") { BookListScreen(navController) }
        composable("detail/{bookId}") { backStackEntry ->
            val bookId = backStackEntry.arguments?.getString("bookId") ?: ""
            BookDetailScreen(bookId, navController)
        }
        composable("favorites") {
            FavoritesScreen(navController)
        }
        composable("settings") { SettingsScreen(navController) }
    }
}