package com.example.domain.validation

sealed class SignInResult {
    object Success : SignInResult()

    data class ValidationError(
        val error: AuthValidationError
    ) : SignInResult()

    data class ServerError(
        val message: String
    ) : SignInResult()
}