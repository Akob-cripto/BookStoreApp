package com.example.bookstoreapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.models.SignInParam
import com.example.domain.models.SignUpParam
import com.example.domain.usecase.SignInUseCase
import com.example.domain.usecase.SignUpUseCase
import com.example.domain.validation.SignInResult
import com.example.domain.validation.SignUpResult

class MainViewModel(
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase
    ): ViewModel() {

    private var singInResultLiveMutable = MutableLiveData<SignInResult>()
    private var signUpResultLiveMutable = MutableLiveData<SignUpResult>()

    val signInResultLiveData: LiveData<SignInResult> = singInResultLiveMutable
    val signUpResultLiveData: LiveData<SignUpResult> = signUpResultLiveMutable




    suspend fun save(email: String, password: String){
        val param = SignUpParam(email = email, password = password)
        val result: SignUpResult = signUpUseCase.execute(param)
        signUpResultLiveMutable.value = result
    }

    suspend fun get(email: String, password: String){
        val param = SignInParam(email = email, password = password)
        val result: SignInResult = signInUseCase.execute(param)
        Log.e("MyLog", "fgbfgsbns $result")
        singInResultLiveMutable.value = result

    }
}