package com.example.domain.usecase

import com.example.domain.models.SignUpParam
import com.example.domain.models.User
import com.example.domain.repository.UserRepository

class SignUpUseCase(private val userRepository: UserRepository){
    suspend fun execute(param: SignUpParam){
        TODO()
    }
}