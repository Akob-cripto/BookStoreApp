package com.example.data.storage.database

import com.example.data.storage.OrderStorage
import com.example.data.storage.models.DataOrder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseOrderStorage(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : OrderStorage {

    override suspend fun createOrder(order: DataOrder): Boolean {
        val uid = auth.currentUser?.uid ?: return false

        return try {
            firestore
                .collection("orders")
                .add(
                    mapOf(
                        "userId" to uid,
                        "userEmail" to order.userEmail,
                        "bookIds" to order.bookIds,
                        "totalPrice" to order.totalPrice,
                        "status" to order.status,
                        "createdAt" to FieldValue.serverTimestamp()
                    )
                )
                .await()

            true
        } catch (e: Exception) {
            false
        }
    }
}