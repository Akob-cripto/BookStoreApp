package com.example.bookstoreapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.SignInParam
import com.example.domain.models.SignUpParam
import com.example.domain.usecase.SignInUseCase
import com.example.domain.usecase.SignUpUseCase
import com.example.domain.validation.SignInResult
import com.example.domain.validation.SignUpResult
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainViewModel(
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private var signInResultLiveMutable = MutableLiveData<SignInResult>()
    private var signUpResultLiveMutable = MutableLiveData<SignUpResult>()

    val signInResultLiveData: LiveData<SignInResult> = signInResultLiveMutable
    val signUpResultLiveData: LiveData<SignUpResult> = signUpResultLiveMutable


    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            val param = SignUpParam(email = email, password = password)
            val result: SignUpResult = signUpUseCase.execute(param)
            signUpResultLiveMutable.value = result
        }
    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            val param = SignInParam(email = email, password = password)
            val result = signInUseCase.execute(param)
            signInResultLiveMutable.value = result
        }
    }
}