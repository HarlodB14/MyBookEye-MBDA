package com.example.mybookeye.Viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mybookeye.DAL.FavoritesRepository
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

    private val _favorites = MutableStateFlow<Set<Book>>(emptySet())
    val favorites = _favorites.asStateFlow()

    fun toggleFavorite(book: Book) {
        _favorites.value = if (_favorites.value.any { it.id == book.id }) {
            _favorites.value.filterNot { it.id == book.id }.toSet()
        } else {
            _favorites.value + book
        }
        FavoritesRepository.saveFavorites(getApplication(), book)
    }


    init {
        loadFavorites()
    }

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

    private fun loadFavorites() {
        _favorites.value = FavoritesRepository.getFavorites(getApplication())
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


    fun isFavorite(bookId: String): Boolean {
        return _favorites.value.any { it.id == bookId }
    }
}