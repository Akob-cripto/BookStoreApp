package com.example.domain.validation

import com.example.domain.models.AuthUser

sealed class SignInResult {
    data class Success(
        val user: AuthUser
    ) : SignInResult()

    data class ValidationError(
        val error: AuthValidationError
    ) : SignInResult()

    data class ServerError(
        val message: String
    ) : SignInResult()

}