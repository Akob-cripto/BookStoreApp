package com.example.domain.repository

import com.example.domain.models.SignInParam
import com.example.domain.models.SignUpParam
import com.example.domain.validation.SignInResult
import com.example.domain.validation.SignUpResult

interface UserRepository {

    suspend fun signIn(
        param: SignInParam
    ) : SignInResult

    suspend fun signUp(
        param: SignUpParam
    ) : SignUpResult

}