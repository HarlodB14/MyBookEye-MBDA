package com.example.mybookeye.Viewmodel

import android.app.Application
import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mybookeye.DAL.FavoritesRepository
import com.example.mybookeye.DAL.FavoritesRepository.getFavorites
import com.example.mybookeye.DAL.FavoritesRepository.saveFavorites
import com.example.mybookeye.Model.Book
import com.example.mybookeye.Service.BookService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class BookViewModel(application: Application) : AndroidViewModel(application) {
    val books = mutableStateListOf<Book>()
    val isLoading = mutableStateOf(false)
    val searchQuery = mutableStateOf("")
    private var searchJob: Job? = null

    private val _favorites = MutableStateFlow<Set<Book>>(emptySet())
    val favorites = _favorites.asStateFlow()

    init {
        viewModelScope.launch {
            _favorites.collectLatest {
            }
        }
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

    fun loadFavorites(context: Context) {
        viewModelScope.launch {
            _favorites.value = getFavorites(context)
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


    fun isFavorite(bookId: String): Boolean {
        return _favorites.value.any { it.id == bookId }
    }
}