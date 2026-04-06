package com.example.data.storage.database

import com.example.data.storage.UserStorage
import com.example.data.storage.models.User
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class FireBase(val auth: FirebaseAuth): UserStorage {
    override suspend fun save(user: User) {
        auth.createUserWithEmailAndPassword(
            user.email,
            user.password
        ).await()
    }

    override suspend fun get(user: User) {
        auth.signInWithEmailAndPassword(
            user.email,
            user.password
        ).await()
    }
}