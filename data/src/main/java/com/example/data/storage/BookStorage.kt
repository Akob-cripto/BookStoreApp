package com.example.data.storage

import com.example.domain.models.Book
import com.example.data.storage.models.DataBook

interface BookStorage {
    suspend fun getBooks(): List<DataBook>
    suspend fun saveBook(book: DataBook): Boolean

    suspend fun addBookToFavorites(bookId: String): Boolean
    suspend fun removeBookFromFavorites(bookId: String): Boolean
}