package com.example.data.storage

import com.example.data.storage.models.DataAuthRequest
import com.example.data.storage.models.DataAuthUser

interface  UserStorage {
    suspend fun signUp(user: DataAuthRequest): DataAuthUser

    suspend fun signIn(user: DataAuthRequest): DataAuthUser

    suspend fun isAdmin(): Boolean
}

