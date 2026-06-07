package com.example.domain.usecase.auth

import com.example.domain.repositories.UserRepository

class SignOutUseCase(private val userRepository: UserRepository) {
    fun execute(){
        userRepository.signOut()
    }
}