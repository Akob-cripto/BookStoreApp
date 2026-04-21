package com.example.data.storage

import com.example.data.storage.models.DataAuthRequest
import com.example.data.storage.models.DataAuthUser
import com.example.domain.models.NewBookParam

interface  UserStorage {
    suspend fun signUpFirebase(user: DataAuthRequest): DataAuthUser

    suspend fun signInFirebase(user: DataAuthRequest): DataAuthUser

    suspend fun isAdminFirebase(): Boolean

    suspend fun saveBookFirebase(param: NewBookParam): Boolean
}

