// Controller/NavController.kt
package com.example.mybookeye.Controller

import android.content.Context
import androidx.navigation.NavHostController
import com.example.mybookeye.Model.Book
import com.example.mybookeye.Model.SelectedBookHolder
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

    fun navigateToBookDetail(book: Book) {
        SelectedBookHolder.selectedBook = book
        val encodedBookId = URLEncoder.encode(book.id, StandardCharsets.UTF_8.toString())
        navController.navigate("detail/$encodedBookId")
    }

    fun navigateToFavorites() {
        navController.navigate("favorites")
    }

    fun navigateToSettings() {
        navController.navigate("settings")
    }

    fun goBack() {
        navController.popBackStack()
    }

    fun getAndroidXNavController(): NavHostController = navController as NavHostController
    fun navigateToSearch() {
        navController.navigate("search")
    }
}