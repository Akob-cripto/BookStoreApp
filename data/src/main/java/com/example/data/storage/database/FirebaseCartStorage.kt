package com.example.data.storage.database

import com.example.data.storage.CartStorage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseCartStorage(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : CartStorage {

    override suspend fun addBookToCart(bookId: String): Boolean {
        val uid = auth.currentUser?.uid ?: return false

        return try {
            firestore
                .collection("users")
                .document(uid)
                .collection("cart")
                .document(bookId)
                .set(
                    mapOf(
                        "bookId" to bookId,
                        "quantity" to 1
                    )
                )
                .await()

            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun removeBookFromCart(bookId: String): Boolean {
        val uid = auth.currentUser?.uid ?: return false

        return try {
            firestore
                .collection("users")
                .document(uid)
                .collection("cart")
                .document(bookId)
                .delete()
                .await()

            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getCartBookIds(): List<String> {
        val uid = auth.currentUser?.uid ?: return emptyList()

        val snapshot = firestore
            .collection("users")
            .document(uid)
            .collection("cart")
            .get()
            .await()

        return snapshot.documents.map { it.id }
    }
}