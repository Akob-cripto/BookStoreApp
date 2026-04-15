package com.example.data.storage.database

import android.util.Log
import com.example.data.storage.UserStorage
import com.example.data.storage.models.DataAuthRequest
import com.example.data.storage.models.DataAuthUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FireBaseStorage(
    val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : UserStorage {
    override suspend fun signUp(user: DataAuthRequest): DataAuthUser {
        try {
            Log.e("MyLog", "firebase save before await")
            auth.createUserWithEmailAndPassword(
                user.email,
                user.password
            ).await()
            Log.e("MyLog", "firebase save after await")
        } catch (e: Exception) {
            Log.e("MyLog", "firebase save error: ${e.message}", e)
            throw e
        }
        return DataAuthUser(email = user.email, userId = auth.currentUser?.uid ?: "")
    }

    override suspend fun signIn(user: DataAuthRequest): DataAuthUser {
        try {
            Log.e("MyLog", "firebase signIn before await")
            auth.signInWithEmailAndPassword(
                user.email,
                user.password
            ).await()
            Log.e("MyLog", "firebase signIn after await")
        } catch (e: Exception) {
            Log.e("MyLog", "firebase signIn error: ${e.message}", e)
            throw e
        }
        return DataAuthUser(email = user.email, userId = auth.currentUser?.uid ?: "")
    }

    override suspend fun isAdmin(): Boolean {
        val uid = auth.currentUser?.uid ?: return false

        return try {
            val doc = firestore.collection("Users")
                .document(uid)
                .get()
                .await()

            doc.getBoolean("isAdmin") == true
        } catch (e: Exception) {
            false
        }
    }
}