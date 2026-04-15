package com.example.bookstoreapp.ui


import android.content.Context
import android.widget.Toast
import com.example.domain.validation.AuthValidationError
import com.google.rpc.context.AttributeContext

fun showAuthValidationError(error: AuthValidationError, context: Context) {
     val message = when(error){
        is AuthValidationError.EmptyEmail -> "Email cannot be empty"
        is AuthValidationError.InvalidEmail -> "Invalid email"
        is AuthValidationError.EmptyPassword -> "Password cannot be empty"
    }

    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}