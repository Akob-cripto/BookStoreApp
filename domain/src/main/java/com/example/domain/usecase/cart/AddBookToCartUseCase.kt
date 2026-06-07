package com.example.domain.usecase.cart

import com.example.domain.repositories.CartRepository

class AddBookToCartUseCase(
    private val cartRepository: CartRepository
) {
    suspend fun execute(bookId: String): Boolean {
        return cartRepository.addBookToCart(bookId)
    }
}