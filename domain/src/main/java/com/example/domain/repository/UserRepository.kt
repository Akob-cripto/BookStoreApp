package com.example.domain.repository

import com.example.domain.models.SignInParam
import com.example.domain.models.SignUpParam
import com.example.domain.models.User
import com.example.domain.usecase.SignInUseCase

interface UserRepository {
    fun SignInUser(param: SignInParam)
    fun SignUpUser(param: SignUpParam)
}