package com.example.bookstoreapp.ui.main_screen

import com.example.domain.models.Book
import com.example.domain.models.Order

data class MainUiState(
    val isLoading: Boolean = false,
    val books: List<Book> = emptyList(),
    val cartBookIds: List<String> = emptyList(),
    val isAdmin: Boolean = false,
    val error: String? = null,
    val isBookSaved: Boolean = false,
    val orders: List<Order> = emptyList()
)