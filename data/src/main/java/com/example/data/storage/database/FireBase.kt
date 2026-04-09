package com.example.data.storage.database

import android.util.Log
import com.example.data.storage.UserStorage
import com.example.data.storage.models.User
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class FireBase(val auth: FirebaseAuth) : UserStorage {
    override suspend fun save(user: User) {
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
    }

    override suspend fun get(user: User) {
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
    }
}