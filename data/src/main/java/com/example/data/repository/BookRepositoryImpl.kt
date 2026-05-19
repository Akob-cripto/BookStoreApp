package com.example.data.repository

import com.example.domain.models.Book
import com.example.data.storage.BookStorage
import com.example.data.storage.models.DataBook
import com.example.domain.models.NewBookParam
import com.example.domain.repositories.BookRepository
import org.jetbrains.annotations.Async

class BookRepositoryImpl(
    private val bookStorage: BookStorage
) : BookRepository {

    override suspend fun getBooks(): List<Book> {
        return bookStorage.getBooks().map { dataBook ->
            Book(
                id = dataBook.id,
                title = dataBook.title,
                author = dataBook.author,
                description = dataBook.description,
                category = dataBook.category,
                imageUri = dataBook.imageUri,
                isFavorite = dataBook.isFavorite
            )
        }
    }
    override suspend fun saveBook(book: NewBookParam): Boolean {
        return bookStorage.saveBook(
            DataBook(
                title = book.title,
                author = book.author,
                description = book.description,
                category = book.category,
                imageUri = book.imageUri
            )
        )
    }

    override suspend fun addBookToFavorites(bookId: String): Boolean {
        return bookStorage.addBookToFavorites(bookId = bookId)
    }

    override suspend fun removeBookFromFavorites(bookId: String): Boolean {
        return bookStorage.removeBookFromFavorites(bookId = bookId)
    }
}