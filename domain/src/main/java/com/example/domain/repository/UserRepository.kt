package com.example.domain.repository

import com.example.domain.models.AuthUser
import com.example.domain.models.NewBookParam
import com.example.domain.models.SignParam
import com.example.domain.validation.SignInResult
import com.example.domain.validation.SignUpResult

interface UserRepository {

    suspend fun signIn(
        param: SignParam
    ) : SignInResult

    suspend fun signUp(
        param: SignParam
    ) : SignUpResult

    suspend fun isCurrentUserAdmin(): Boolean

    suspend fun saveBook(param: NewBookParam): Boolean

}