package com.example.domain.validation

import com.example.domain.models.AuthUser

sealed class SignUpResult {
    data class Success(
        val user: AuthUser
    ) : SignUpResult()

    data class ValidationError(
        val error: AuthValidationError
    ) : SignUpResult()

    data class ServerError(
        val message: String
    ) : SignUpResult()
}