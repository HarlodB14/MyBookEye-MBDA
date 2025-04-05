// Nav/Navigation.kt
package com.example.mybookeye.Nav

import BookDetailScreen
import BookListScreen
import SettingsScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mybookeye.Controller.NavController


@Composable
fun Navigation(navController: NavController) {
    NavHost(
        navController = navController.getAndroidXNavController(), // Use the underlying NavHostController
        startDestination = "booklist"
    ) {
        composable("booklist") { BookListScreen(navController) }
        composable("detail/{bookId}") { backStackEntry ->
            val bookId = backStackEntry.arguments?.getString("bookId") ?: ""
            BookDetailScreen(bookId, navController)
        }
        composable("settings") { SettingsScreen(navController) }
    }
}