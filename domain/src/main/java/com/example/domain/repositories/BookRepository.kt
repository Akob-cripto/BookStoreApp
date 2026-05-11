package com.example.domain.repositories

import com.example.domain.models.Book
import com.example.domain.models.NewBookParam

interface BookRepository {
    suspend fun getBooks(): List<Book>
    suspend fun saveBook(book: NewBookParam): Boolean
}