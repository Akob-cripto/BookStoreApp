package com.example.domain.usecase

import com.example.domain.repositories.UserRepository

class SignOutUseCase(private val userRepository: UserRepository) {
    fun execute(){
        userRepository.signOut()
    }
}