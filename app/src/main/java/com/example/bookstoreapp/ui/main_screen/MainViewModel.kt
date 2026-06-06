package com.example.bookstoreapp.ui.main_screen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookstoreapp.ui.main_screen.MainUiState
import com.example.domain.models.Book
import com.example.domain.models.NewBookParam
import com.example.domain.models.SignParam
import com.example.domain.usecase.AddBookToCartUseCase
import com.example.domain.usecase.AddBookToFavoritesUseCase
import com.example.domain.usecase.CheckIsAdminUseCase
import com.example.domain.usecase.GetBooksUseCase
import com.example.domain.usecase.GetCartBookIdsUseCase
import com.example.domain.usecase.RemoveBookFromCartUseCase
import com.example.domain.usecase.RemoveBookFromFavoritesUseCase
import com.example.domain.usecase.SaveBookUseCase
import com.example.domain.usecase.SignInUseCase
import com.example.domain.usecase.SignOutUseCase
import com.example.domain.usecase.SignUpUseCase
import com.example.domain.validation.SignInResult
import com.example.domain.validation.SignUpResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val checkIsAdminUseCase: CheckIsAdminUseCase,
    private val getBooksUseCase: GetBooksUseCase,
    private val saveBookUseCase: SaveBookUseCase,
    private val removeBookFromFavoritesUseCase: RemoveBookFromFavoritesUseCase,
    private val addBookToFavoritesUseCase: AddBookToFavoritesUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val addBookToCartUseCase: AddBookToCartUseCase,
    private val removeBookFromCartUseCase: RemoveBookFromCartUseCase,
    private val getCartBookIdsUseCase: GetCartBookIdsUseCase
) : ViewModel() {

    val cartBookIds: List<String> = emptyList()

    private val signInResultLiveMutable = MutableLiveData<SignInResult?>(null)
    private val signUpResultLiveMutable = MutableLiveData<SignUpResult?>(null)

    private val isAdminResultLiveMutable = MutableLiveData<Boolean>(false)

    val signInResultLiveData: LiveData<SignInResult?> = signInResultLiveMutable
    val signUpResultLiveData: LiveData<SignUpResult?> = signUpResultLiveMutable

    private val _mainUiState = MutableStateFlow(MainUiState())
    val mainUiState = _mainUiState.asStateFlow()

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
        author: String,
        price: Double
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
                    author = author,
                    price = price
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
        Log.d("MyLog", "checkIsAdmin called")

        viewModelScope.launch {
            val result = checkIsAdminUseCase.execute()

            Log.d("MyLog", "admin result = $result")

            _mainUiState.value = _mainUiState.value.copy(
                isAdmin = result
            )
        }
    }

    fun loadCart() {
        viewModelScope.launch {
            try {
                val cartBookIds = getCartBookIdsUseCase.execute()

                _mainUiState.value = _mainUiState.value.copy(
                    cartBookIds = cartBookIds
                )
            } catch (e: Exception) {
                _mainUiState.value = _mainUiState.value.copy(
                    error = e.message ?: "Ошибка загрузки корзины"
                )
            }
        }
    }

    fun addBookToCart(bookId: String) {
        viewModelScope.launch {
            try {
                val result = addBookToCartUseCase.execute(bookId)

                if (result) {
                    loadCart()
                } else {
                    _mainUiState.value = _mainUiState.value.copy(
                        error = "Не удалось добавить книгу в корзину"
                    )
                }
            } catch (e: Exception) {
                _mainUiState.value = _mainUiState.value.copy(
                    error = e.message ?: "Ошибка добавления книги в корзину"
                )
            }
        }
    }

    fun removeBookFromCart(bookId: String) {
        viewModelScope.launch {
            try {
                val result = removeBookFromCartUseCase.execute(bookId)

                if (result) {
                    loadCart()
                } else {
                    _mainUiState.value = _mainUiState.value.copy(
                        error = "Не удалось удалить книгу из корзины"
                    )
                }
            } catch (e: Exception) {
                _mainUiState.value = _mainUiState.value.copy(
                    error = e.message ?: "Ошибка удаления книги из корзины"
                )
            }
        }
    }


    fun onCartClick(bookId: String) {
        val isInCart = _mainUiState.value.cartBookIds.contains(bookId)

        if (isInCart) {
            removeBookFromCart(bookId)
        } else {
            addBookToCart(bookId)
        }
    }

    fun clearSignInResult() {
        signInResultLiveMutable.value = null
    }

    fun clearSignUpResult() {
        signUpResultLiveMutable.value = null
    }

    fun onFavoriteClick(book: Book) {
        viewModelScope.launch {
            val result = if (book.isFavorite) {
                removeBookFromFavoritesUseCase.execute(book.id)
            } else {
                addBookToFavoritesUseCase.execute(book.id)
            }

            if (result) {
                loadBooks()
            }
        }
    }

    fun signOut() {
        signOutUseCase.execute()

        _mainUiState.value = _mainUiState.value.copy(
            isAdmin = false,
            books = emptyList(),
            error = null,
            isLoading = false
        )
    }
}