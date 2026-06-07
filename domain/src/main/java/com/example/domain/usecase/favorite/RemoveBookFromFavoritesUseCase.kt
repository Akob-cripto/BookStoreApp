package com.example.domain.usecase.favorite

import com.example.domain.repositories.BookRepository

class RemoveBookFromFavoritesUseCase(
    private val bookRepository: BookRepository
) {
    suspend fun execute(bookId: String): Boolean {
        return bookRepository.removeBookFromFavorites(bookId)
    }
}