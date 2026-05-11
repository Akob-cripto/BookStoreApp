package com.example.data.storage.database

import com.example.data.storage.BookStorage
import com.example.data.storage.models.DataBook
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseBookStorage(
    private val firestore: FirebaseFirestore
) : BookStorage {

    override suspend fun getBooks(): List<DataBook> {
        val snapshot = firestore
            .collection("books")
            .get()
            .await()

        return snapshot.documents.map { doc ->
            DataBook(
                id = doc.id,
                title = doc.getString("title") ?: "",
                author = doc.getString("author") ?: "",
                description = doc.getString("description") ?: "",
                category = doc.getString("category") ?: "",
                imageUri = doc.getString("imageUri") ?: ""
            )
        }
    }

    override suspend fun saveBook(book: DataBook): Boolean {
        return try {
            firestore
                .collection("books")
                .add(
                    mapOf(
                        "title" to book.title,
                        "author" to book.author,
                        "description" to book.description,
                        "category" to book.category,
                        "imageUri" to book.imageUri
                    )
                )
                .await()

            true
        } catch (e: Exception) {
            false
        }
    }
}