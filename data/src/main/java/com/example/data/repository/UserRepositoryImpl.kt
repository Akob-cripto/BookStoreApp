package com.example.data.repository


import android.util.Log
import com.example.data.storage.UserStorage
import com.example.data.storage.database.FireBase
import com.example.data.storage.models.User
import com.example.domain.models.SignInParam
import com.example.domain.models.SignUpParam
import com.example.domain.repository.UserRepository
import com.example.domain.validation.SignInResult
import com.example.domain.validation.SignUpResult

class UserRepositoryImpl(private val fire: UserStorage): UserRepository {


    override suspend fun signIn(param: SignInParam): SignInResult {
        Log.e("MyLog", "signIn: ${param.email}")
        val user = InParamToUser(param)
        return try{
           fire.get(user)
           SignInResult.Success
       } catch (e: Exception) {
           SignInResult.ServerError(e.message.toString())
       }
    }

    override suspend fun signUp(param: SignUpParam): SignUpResult {
        Log.e("MyLog", "signIn: ${param.email}")
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