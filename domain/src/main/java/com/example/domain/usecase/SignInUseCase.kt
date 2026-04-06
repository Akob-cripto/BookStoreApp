package com.example.domain.usecase

import com.example.domain.models.SignInParam
import com.example.domain.models.SignUpParam
import com.example.domain.repository.UserRepository
import com.example.domain.validation.AuthValidationError
import com.example.domain.validation.SignInResult

class SignInUseCase(private val userRepository: UserRepository) {
    suspend fun execute(param: SignInParam): SignInResult {
        val email = param.email
        val password = param.password

        if (email.isBlank()) {
            return SignInResult.ValidationError(AuthValidationError.EmptyEmail)
        }

        if (!isValidEmail(email)) {
            return SignInResult.ValidationError(AuthValidationError.InvalidEmail)
        }

        if (password.isBlank()) {
            return SignInResult.ValidationError(AuthValidationError.EmptyPassword)
        }
        return userRepository.signIn(SignInParam(email = email, password = password))
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
        return emailRegex.matches(email)
    }

}