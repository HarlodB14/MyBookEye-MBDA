package com.example.mybookeye.Service

import android.content.Context
import android.os.Handler
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.mybookeye.DAL.FavoritesRepository
import com.example.mybookeye.Model.Book
import kotlinx.coroutines.Job
import java.net.URLEncoder

object BookService {
    //main zoek en feautred boeken methode
    private var searchJob: Job? = null
    private fun fetchBookData(
        context: Context,
        url: String,
        onResult: (List<Book>) -> Unit,
        retryCount: Int = 2
    ) {
        val queue = Volley.newRequestQueue(context)

        val request = object : JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val docs = response.getJSONArray("docs")
                val books = mutableListOf<Book>()
                for (i in 0 until docs.length()) {
                    val obj = docs.getJSONObject(i)
                    val title = obj.optString("title")
                    val author = obj.optJSONArray("author_name")?.optString(0) ?: "Unknown"
                    val id = obj.optString("key")
                    val coverKey = obj.optString("cover_edition_key", "")
                    val coverUrl = if (coverKey.isNotEmpty()) {
                        "https://covers.openlibrary.org/b/olid/$coverKey-M.jpg"
                    } else {
                        ""
                    }
                    books.add(Book(id, title, author, coverUrl))
                }
                onResult(books)
            },
            { error ->
                if (retryCount > 0) {
                    // Retry after a delay
                    Handler(context.mainLooper).postDelayed({
                        fetchBookData(context, url, onResult, retryCount - 1)
                    }, 1000) // 1 second delay
                } else {
                    onResult(emptyList())
                }
            }) {}

        queue.add(request)
    }


    //featured lijst ophalen
    fun fetchBooks(context: Context, onResult: (List<Book>) -> Unit) {
        val url = "https://openlibrary.org/search.json?q=android"
        fetchBookData(context, url, onResult)
    }

    fun searchBooks(context: Context, query: String, onResult: (List<Book>) -> Unit) {
        if (query.isBlank()) {
            onResult(emptyList())
            return
        }

        val encodedQuery = URLEncoder.encode(query, "UTF-8")
        val url = "https://openlibrary.org/search.json?q=$encodedQuery"
        fetchBookData(context, url, onResult)
    }


    fun saveFavorite(context: Context, book: Book) {
        FavoritesRepository.toggleFavorite(context, book)
    }

}