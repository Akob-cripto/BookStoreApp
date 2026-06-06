package com.example.bookstoreapp.ui.auth


import android.content.Context
import android.widget.Toast
import com.example.domain.validation.AuthValidationError

fun showAuthValidationError(error: AuthValidationError, context: Context) {
     val message = when(error){
        is AuthValidationError.EmptyEmail -> "Email cannot be empty"
        is AuthValidationError.InvalidEmail -> "Invalid email"
        is AuthValidationError.EmptyPassword -> "Password cannot be empty"
    }

    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}