package com.example.domain.repositories

interface CartRepository {
    suspend fun addBookToCart(bookId: String): Boolean
    suspend fun removeBookFromCart(bookId: String): Boolean
    suspend fun getCartBookIds(): List<String>
}