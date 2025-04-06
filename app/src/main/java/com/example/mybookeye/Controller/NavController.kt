// Controller/NavController.kt
package com.example.mybookeye.Controller

import android.content.Context
import androidx.navigation.NavHostController
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import androidx.navigation.NavController as AndroidXNavController

class NavController(private val navController: AndroidXNavController) {
    val context: Context get() = navController.context

    fun navigateToBookList() {
        navController.navigate("booklist") {
            popUpTo("booklist") { inclusive = true }
        }
    }

    fun navigateToBookDetail(bookId: String) {
        val cleanId = bookId.removePrefix("/")  // Remove leading slash if needed
        val encodedId =
            URLEncoder.encode(cleanId, StandardCharsets.UTF_8.toString())  // Encode the book ID
        navController.navigate("detail/$encodedId")
    }

    fun navigateToSettings() {
        navController.navigate("settings")
    }

    fun goBack() {
        navController.popBackStack()
    }

    fun getAndroidXNavController(): NavHostController = navController as NavHostController
}