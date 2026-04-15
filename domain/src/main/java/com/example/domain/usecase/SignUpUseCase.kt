package com.example.domain.usecase

import com.example.domain.models.AuthUser
import com.example.domain.models.SignParam
import com.example.domain.repository.UserRepository
import com.example.domain.validation.AuthValidationError
import com.example.domain.validation.SignUpResult


class SignUpUseCase(private val userRepository: UserRepository) {
    suspend fun execute(param: SignParam): SignUpResult {
        val email = param.email
        val password = param.password

        if (email.isBlank()) {
            return SignUpResult.ValidationError(AuthValidationError.EmptyEmail)
        }
        if (password.isBlank()) {
            return SignUpResult.ValidationError(AuthValidationError.EmptyPassword)
        }

        if (!isValidEmail(email)) {
            return SignUpResult.ValidationError(AuthValidationError.InvalidEmail)
        }

        return userRepository.signUp(
            SignParam(
                email = email,
                password = password
            )
        )
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
        return emailRegex.matches(email)
    }
}