package com.example.data.storage

import com.example.data.storage.models.User
import com.google.firebase.auth.AuthResult

interface  UserStorage {
    suspend fun save(user: User)

    suspend fun get(user: User)
}

