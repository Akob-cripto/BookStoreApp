package com.example.data.repository

import com.example.data.storage.CartStorage
import com.example.domain.repositories.CartRepository

class CartRepositoryImpl(
    private val cartStorage: CartStorage
): CartRepository {
    override suspend fun addBookToCart(bookId: String): Boolean {
        return cartStorage.addBookToCart(bookId = bookId)
    }

    override suspend fun removeBookFromCart(bookId: String): Boolean {
        return cartStorage.removeBookFromCart(bookId = bookId)
    }

    override suspend fun getCartBookIds(): List<String> {
        return cartStorage.getCartBookIds()
    }
}