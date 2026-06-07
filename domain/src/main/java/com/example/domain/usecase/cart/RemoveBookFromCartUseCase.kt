package com.example.domain.usecase.cart

import com.example.domain.repositories.CartRepository

class RemoveBookFromCartUseCase(
    private val cartRepository: CartRepository
) {
    suspend fun execute(bookId: String): Boolean {
        return cartRepository.removeBookFromCart(bookId)
    }
}