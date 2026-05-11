package com.example.bookstoreapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookstoreapp.models.MainUiState
import com.example.domain.models.AuthUser
import com.example.domain.models.Book
import com.example.domain.models.NewBookParam
import com.example.domain.models.SignParam
import com.example.domain.usecase.CheckIsAdminUseCase
import com.example.domain.usecase.GetBooksUseCase
import com.example.domain.usecase.SaveBookUseCase
import com.example.domain.usecase.SignInUseCase
import com.example.domain.usecase.SignUpUseCase
import com.example.domain.validation.SignInResult
import com.example.domain.validation.SignUpResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.String

class MainViewModel(
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val checkIsAdminUseCase: CheckIsAdminUseCase,
    private val getBooksUseCase: GetBooksUseCase,
    private val saveBookUseCase: SaveBookUseCase
) : ViewModel() {

    private val signInResultLiveMutable = MutableLiveData<SignInResult?>(null)
    private val signUpResultLiveMutable = MutableLiveData<SignUpResult?>(null)

    private val isAdminResultLiveMutable = MutableLiveData<Boolean>(false)

    val signInResultLiveData: LiveData<SignInResult?> = signInResultLiveMutable
    val signUpResultLiveData: LiveData<SignUpResult?> = signUpResultLiveMutable

    val isAdminLiveData: LiveData<Boolean> = isAdminResultLiveMutable

    private val _mainUiState = MutableStateFlow(MainUiState())
    val mainUiState = _mainUiState.asStateFlow()


    init {
        loadBooks()
    }

    fun loadBooks() {
        viewModelScope.launch {
            _mainUiState.value = _mainUiState.value.copy(
                isLoading = true,
                error = null
            )

            try {
                val books = getBooksUseCase.execute()

                _mainUiState.value = _mainUiState.value.copy(
                    isLoading = false,
                    books = books
                )
            } catch (e: Exception) {
                _mainUiState.value = _mainUiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Ошибка загрузки книг"
                )
            }
        }
    }

    fun saveBook(
        category: String,
        imageUri: String,
        title: String,
        description: String,
        author: String
    ) {
        viewModelScope.launch {
            _mainUiState.value = _mainUiState.value.copy(
                isLoading = true,
                error = null,
                isBookSaved = false
            )

            try {
                val book = NewBookParam(
                    category = category,
                    imageUri = imageUri,
                    title = title,
                    description = description,
                    author = author
                )

                val result = withContext(Dispatchers.IO) {
                    saveBookUseCase.execute(book)
                }

                if (result) {
                    _mainUiState.value = _mainUiState.value.copy(
                        isLoading = false,
                        isBookSaved = true
                    )

                    loadBooks()
                } else {
                    _mainUiState.value = _mainUiState.value.copy(
                        isLoading = false,
                        error = "Не удалось сохранить книгу"
                    )
                }

            } catch (e: Exception) {
                _mainUiState.value = _mainUiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Ошибка сохранения книги"
                )
            }
        }
    }

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
            Log.d("MyLog", "in signIn fun")
            val param = SignParam(email = email, password = password)
            val result = signInUseCase.execute(param)
            signInResultLiveMutable.value = result
        }
    }

    fun checkIsAdmin() {
        viewModelScope.launch {
            Log.e("MyLog", "${isAdminResultLiveMutable.value}")
            isAdminResultLiveMutable.value = checkIsAdminUseCase.execute()
            Log.e("MyLog", "${isAdminResultLiveMutable.value}")
        }
    }

    fun clearSignInResult() {
        signInResultLiveMutable.value = null
    }

    fun clearSignUpResult() {
        signUpResultLiveMutable.value = null
    }
}