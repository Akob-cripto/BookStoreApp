package com.example.domain.usecase

import com.example.domain.repositories.CartRepository

class RemoveBookFromCartUseCase(
    private val cartRepository: CartRepository
) {
    suspend fun execute(bookId: String): Boolean {
        return cartRepository.removeBookFromCart(bookId)
    }
}