package com.example.domain.usecase

import com.example.domain.models.Book
import com.example.domain.repositories.BookRepository

class GetBooksUseCase(
    private val bookRepository: BookRepository
) {
    suspend fun execute(): List<Book> {
        return bookRepository.getBooks()
    }
}