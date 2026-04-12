package com.example.domain.validation

sealed class SignUpResult {
    object Success : SignUpResult()

    class ValidationError(
        val error: AuthValidationError
    ) : SignUpResult()

    data class ServerError(
        val message: String
    ) : SignUpResult()
}