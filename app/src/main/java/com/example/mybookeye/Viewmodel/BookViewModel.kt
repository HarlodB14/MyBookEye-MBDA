package com.example.mybookeye.Viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mybookeye.Model.Book
import com.example.mybookeye.Service.BookService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BookViewModel(application: Application) : AndroidViewModel(application) {
    val books = mutableStateListOf<Book>()
    val isLoading = mutableStateOf(false)
    val searchQuery = mutableStateOf("")
    private var searchJob: Job? = null

    private val _favorites = MutableStateFlow<List<Book>>(emptyList())
    val favorites = _favorites.asStateFlow()

    fun loadBooks() {
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            BookService.fetchBooks(getApplication()) { result ->
                books.clear()
                books.addAll(result)
                isLoading.value = false
            }
        }
    }

    fun searchBooks(query: String) {
        searchQuery.value = query
        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            delay(400)
            if (query.isNotBlank()) {
                isLoading.value = true
                BookService.searchBooks(getApplication(), query) { result ->
                    books.clear()
                    books.addAll(result)
                    isLoading.value = false
                }
            }
        }
    }

    fun toggleFavorite(book: Book) {
        _favorites.value = if (_favorites.value.any { it.id == book.id }) {
            _favorites.value.filterNot { it.id == book.id }
        } else {
            _favorites.value + book
        }
        BookService.saveFavorite(getApplication(), book)
    }

    fun isFavorite(bookId: String): Boolean {
        return _favorites.value.any { it.id == bookId }
    }
}