package com.example.domain.usecase

import com.example.domain.models.SignInParam
import com.example.domain.repository.UserRepository

class SignInUseCase(private val userRepository: UserRepository) {
    suspend fun execute(param: SignInParam){
        TODO()
    }
}