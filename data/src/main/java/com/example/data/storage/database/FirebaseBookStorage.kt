package com.example.data.storage.database

import android.util.Log
import com.example.data.storage.BookStorage
import com.example.data.storage.models.DataBook
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseBookStorage(
    private val firestore: FirebaseFirestore,
    val auth: FirebaseAuth
) : BookStorage {

    override suspend fun getBooks(): List<DataBook> {
        val uid = auth.currentUser?.uid ?: return emptyList()


        val booksSnapshot = firestore
            .collection("books")
            .get()
            .await()

        val favoritesSnapshot = firestore
            .collection("users")
            .document(uid)
            .collection("favorites")
            .get()
            .await()

        val favoriteIds: Set<String> = favoritesSnapshot.documents
            .map { it.id }
            .toSet()

        Log.d("MyLog", favoriteIds.toString())

        return booksSnapshot.documents.map { doc ->
            DataBook(
                id = doc.id,
                title = doc.getString("title") ?: "",
                author = doc.getString("author") ?: "",
                description = doc.getString("description") ?: "",
                category = doc.getString("category") ?: "",
                imageUri = doc.getString("imageUri") ?: "",
                isFavorite = favoriteIds.contains(doc.id)
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

    override suspend fun addBookToFavorites(bookId: String): Boolean {
        val uid = auth.currentUser?.uid ?: return false

        return try {
            firestore
                .collection("users")
                .document(uid)
                .collection("favorites")
                .document(bookId)
                .set(mapOf("bookId" to bookId))
                .await()

            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun removeBookFromFavorites(bookId: String): Boolean {
        val uid = auth.currentUser?.uid ?: return false

        return try {
            firestore
                .collection("users")
                .document(uid)
                .collection("favorites")
                .document(bookId)
                .delete()
                .await()

            true
        } catch (e: Exception) {
            false
        }
    }
}