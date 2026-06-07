package com.example.domain.usecase.auth

import com.example.domain.repositories.UserRepository

class CheckIsAdminUseCase(private val userRepository: UserRepository) {
    suspend fun execute(): Boolean {
         return userRepository.isCurrentUserAdmin()
    }
}