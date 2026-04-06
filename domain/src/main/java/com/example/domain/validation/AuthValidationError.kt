package com.example.domain.validation

sealed class AuthValidationError {
    object EmptyEmail : AuthValidationError()
    object InvalidEmail : AuthValidationError()
    object EmptyPassword : AuthValidationError()
}