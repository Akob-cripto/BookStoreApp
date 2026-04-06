package com.example.data.repository


import com.example.data.storage.database.FireBase
import com.example.data.storage.models.User
import com.example.domain.models.SignInParam
import com.example.domain.models.SignUpParam
import com.example.domain.repository.UserRepository
import com.example.domain.validation.SignInResult
import com.example.domain.validation.SignUpResult
import com.google.firebase.auth.AuthResult

import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.annotations.Async


class UserRepositoryImpl( private val auth: FirebaseAuth): UserRepository {
    val fire = FireBase(auth)

    override suspend fun signIn(param: SignInParam): SignInResult {
        val user = InParamToUser(param)
        return try{
           fire.get(user)
           SignInResult.Success
       } catch (e: Exception) {
           SignInResult.ServerError(e.message.toString())
       }
    }

    override suspend fun signUp(param: SignUpParam): SignUpResult {
        val user = UpParamToUser(param)
        return try{
            fire.save(user)
            SignUpResult.Success
        } catch (e: Exception) {
            SignUpResult.ServerError(e.message.toString() ?: "Sign up failed")
        }
    }


    fun InParamToUser(param: SignInParam): User {
        return User(email = param.email, password = param.password)
    }

    fun UpParamToUser(param: SignUpParam): User {
        return User(email = param.email, password = param.password)
    }
}