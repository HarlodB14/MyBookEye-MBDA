package com.example.mybookeye.Viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mybookeye.Model.Book
import com.example.mybookeye.Service.BookSerivce
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookViewModel(application: Application) : AndroidViewModel(application) {
    val books = mutableStateListOf<Book>()

    fun loadBooks() {
        viewModelScope.launch(Dispatchers.IO) {
            BookSerivce.fetchBooks(getApplication()) {
                books.clear()
                books.addAll(it)
            }
        }
    }
}