package com.example.bookstoreapp.models

import com.example.domain.models.Book

data class MainUiState(
    val isLoading: Boolean = false,
    val books: List<Book> = emptyList(),
    val error: String? = null,
    val isBookSaved: Boolean = false
)