package com.example.data.storage

interface CartStorage {
    suspend fun addBookToCart(bookId: String): Boolean
    suspend fun removeBookFromCart(bookId: String): Boolean
    suspend fun getCartBookIds(): List<String>
}