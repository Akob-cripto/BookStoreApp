package com.example.bookstoreapp.ui.main_screen

import com.example.domain.models.Book

data class MainUiState(
    val isLoading: Boolean = false,
    val books: List<Book> = emptyList(),
    val cartBookIds: List<String> = emptyList(),
    val isAdmin: Boolean = false,
    val error: String? = null,
    val isBookSaved: Boolean = false
)