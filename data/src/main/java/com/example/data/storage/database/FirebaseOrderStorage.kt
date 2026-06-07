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

    override suspend fun getMyOrders(): List<DataOrder> {
        val uid = auth.currentUser?.uid ?: return emptyList()

        val snapshot = firestore
            .collection("orders")
            .whereEqualTo("userId", uid)
            .get()
            .await()

        return snapshot.documents.map { doc ->
            DataOrder(
                id = doc.id,
                userId = doc.getString("userId") ?: "",
                userEmail = doc.getString("userEmail") ?: "",
                bookIds = doc.get("bookIds") as? List<String> ?: emptyList(),
                totalPrice = doc.getDouble("totalPrice") ?: 0.0,
                status = doc.getString("status") ?: "created",
                createdAtMillis = doc.getTimestamp("createdAt")?.toDate()?.time ?: 0L
            )
        }.sortedByDescending { it.createdAtMillis }
    }

    override suspend fun cancelOrder(orderId: String): Boolean {
        return try {
            firestore
                .collection("orders")
                .document(orderId)
                .update("status", "canceled")
                .await()

            true
        } catch (e: Exception) {
            false
        }
    }
}