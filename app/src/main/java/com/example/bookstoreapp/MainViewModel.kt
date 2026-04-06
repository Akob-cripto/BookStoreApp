package com.example.bookstoreapp

import androidx.lifecycle.ViewModel
import com.example.domain.models.SignInParam
import com.example.domain.models.SignUpParam
import com.example.domain.usecase.SignInUseCase
import com.example.domain.usecase.SignUpUseCase

class MainViewModel(
    private val SignInUseCase: SignInUseCase,
    private val SignUpUseCase: SignUpUseCase
    ): ViewModel() {


    suspend fun save(email: String, password: String){
        val param = SignUpParam(email = email, password = password)
        SignUpUseCase.execute(param)
    }

    suspend fun get(email: String, password: String){
        val param = SignInParam(email = email, password = password)
        SignInUseCase.execute(param)
    }
}