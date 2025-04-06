package com.example.mybookeye.Service

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.mybookeye.Model.Book

object BookService {
    fun fetchBooks(context: Context, onResult: (List<Book>) -> Unit) {
        val queue = Volley.newRequestQueue(context)
        val url = "https://openlibrary.org/search.json?q=android"

        val request = JsonObjectRequest(Request.Method.GET, url, null, { response ->
            val docs = response.getJSONArray("docs")
            val books = mutableListOf<Book>()
            for (i in 0 until docs.length()) {
                val obj = docs.getJSONObject(i)
                val title = obj.optString("title")
                val author = obj.optJSONArray("author_name")?.optString(0) ?: "Unknown"
                val id = obj.optString("key")
                val coverUrl =
                    "https://covers.openlibrary.org/b/olid/${obj.optString("cover_edition_key")}-M.jpg"
                books.add(Book(id, title, author, coverUrl))
            }
            onResult(books)
        }, {
            onResult(emptyList())
        })
        queue.add(request)
    }

}