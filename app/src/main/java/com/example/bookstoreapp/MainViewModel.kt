package com.example.bookstoreapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.domain.models.AuthUser
import com.example.domain.models.NewBookParam
import com.example.domain.models.SignParam
import com.example.domain.usecase.CheckIsAdminUseCase
import com.example.domain.usecase.SaveBookUseCase
import com.example.domain.usecase.SignInUseCase
import com.example.domain.usecase.SignUpUseCase
import com.example.domain.validation.SignInResult
import com.example.domain.validation.SignUpResult
import kotlinx.coroutines.launch
import kotlin.String

class MainViewModel(
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val checkIsAdminUseCase: CheckIsAdminUseCase,
    private val saveBookUseCase: SaveBookUseCase
) : ViewModel() {

    private val signInResultLiveMutable = MutableLiveData<SignInResult?>(null)
    private val signUpResultLiveMutable = MutableLiveData<SignUpResult?>(null)

    private val isAdminResultLiveMutable = MutableLiveData<Boolean>(false)

    val signInResultLiveData: LiveData<SignInResult?> = signInResultLiveMutable
    val signUpResultLiveData: LiveData<SignUpResult?> = signUpResultLiveMutable

    val isAdminLiveData: LiveData<Boolean> = isAdminResultLiveMutable


    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            val param = SignParam(email = email, password = password)
            val result = signUpUseCase.execute(param)
            Log.d("MyLog", "${result}")
            signUpResultLiveMutable.value = result
        }
    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            val param = SignParam(email = email, password = password)
            val result = signInUseCase.execute(param)
            signInResultLiveMutable.value = result
        }
    }

    fun checkIsAdmin(){
        viewModelScope.launch {
            Log.e("MyLog", "${isAdminResultLiveMutable.value}")
            isAdminResultLiveMutable.value = checkIsAdminUseCase.execute()
            Log.e("MyLog", "${isAdminResultLiveMutable.value}")
        }
    }

    fun saveBook(
        category: String,
        imageUri: String,
        title: String,
        description: String){
        viewModelScope.launch {
             val param = NewBookParam(
             category = category,
             imageUri= imageUri,
             title= title,
             description= description)

            Log.d("MyLog", "In saveBook")

            val result = saveBookUseCase.execute(param)
        }
    }

     fun clearSignInResult() {
        signInResultLiveMutable.value = null
    }

     fun clearSignUpResult() {
        signUpResultLiveMutable.value = null
    }
}