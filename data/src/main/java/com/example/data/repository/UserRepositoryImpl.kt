package com.example.data.repository


import android.util.Log
import com.example.data.storage.UserStorage
import com.example.data.storage.models.DataAuthRequest
import com.example.data.storage.models.DataAuthUser
import com.example.domain.models.AuthUser
import com.example.domain.models.SignParam
import com.example.domain.repository.UserRepository
import com.example.domain.validation.SignInResult
import com.example.domain.validation.SignUpResult

class UserRepositoryImpl(private val fire: UserStorage) : UserRepository {


    override suspend fun signIn(param: SignParam): SignInResult {
        Log.e("MyLog", "signIn: ${param.email}")
        val user = fromSignParamToDataUserRequest(param)
        return try {
            val AuthUser = fromDataAuthUserToAuthUser(fire.signIn(user))
            SignInResult.Success(AuthUser)
        } catch (e: Exception) {
            SignInResult.ServerError(e.message.toString() ?: "Sign in failed")
        }
    }

    override suspend fun signUp(user: SignParam): SignUpResult {
        Log.e("MyLog", "signIn: ${user.email}")
        val user = fromSignParamToDataUserRequest(user)
        return try {
            val AuthUser = fromDataAuthUserToAuthUser(fire.signUp(user))
            SignUpResult.Success(AuthUser)
        } catch (e: Exception) {
            SignUpResult.ServerError(e.message.toString() ?: "Sign up failed")
        }
    }

    override suspend fun isCurrentUserAdmin(): Boolean {
        return fire.isAdmin()
    }

    fun fromSignParamToDataUserRequest(user: SignParam): DataAuthRequest {
        return DataAuthRequest(email = user.email, password = user.password)
    }

    fun fromDataAuthUserToAuthUser(user: DataAuthUser): AuthUser {
        return AuthUser(userId = user.userId, email = user.email)
    }
}