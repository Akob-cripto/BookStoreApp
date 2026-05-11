package com.example.domain.usecase

import com.example.domain.models.NewBookParam
import com.example.domain.repositories.BookRepository

class SaveBookUseCase(
    private val bookRepository: BookRepository
) {
    suspend  fun execute(book: NewBookParam): Boolean {
        return bookRepository.saveBook(book)
    }
}