package com.example.mybookeye.DAL

import android.content.Context
import com.example.mybookeye.Model.Book
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import androidx.core.content.edit

object FavoritesRepository {
    private const val PREFS_NAME = "favorites_prefs"
    private const val FAVORITES_KEY = "favorites_list"
    private val gson = Gson()

    fun saveFavorites(context: Context, favorites: Set<Book>) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = gson.toJson(favorites)
        prefs.edit() { putString(FAVORITES_KEY, json) }
    }

    fun getFavorites(context: Context): Set<Book> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(FAVORITES_KEY, null)
        return if (json != null) {
            val type = object : TypeToken<Set<Book>>() {}.type
            gson.fromJson(json, type) ?: emptySet()
        } else {
            emptySet()
        }
    }

    fun isFavorite(context: Context, bookId: String): Boolean {
        return getFavorites(context).any { it.id == bookId }
    }

    fun toggleFavorite(context: Context, book: Book) {
        val favorites = getFavorites(context).toMutableSet()
        if (favorites.any { it.id == book.id }) {
            favorites.removeIf { it.id == book.id }
        } else {
            favorites.add(book)
        }
        saveFavorites(context, favorites)
    }
}
