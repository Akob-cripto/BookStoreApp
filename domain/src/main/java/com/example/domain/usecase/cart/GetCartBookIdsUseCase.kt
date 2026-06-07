package com.example.domain.usecase.cart

import com.example.domain.repositories.CartRepository

class GetCartBookIdsUseCase(
    private val cartRepository: CartRepository
) {
    suspend fun execute(): List<String> {
        return cartRepository.getCartBookIds()
    }
}