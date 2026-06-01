package com.example.bookstoreapp.ui.main_screen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.bookstoreapp.MainViewModel
import com.example.bookstoreapp.ui.main_screen.buttiom_menu.BottomMenu
import com.example.bookstoreapp.ui.main_screen.buttiom_menu.BottomScreen
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun MainScreen(
    email: String,
    userId: String,
    navController: NavController,
    vm: MainViewModel
) {
    val drawerState = rememberDrawerState(DrawerValue.Open)

    var selectedScreen by remember {
        mutableStateOf(BottomScreen.Books)
    }

    var selectedCategory by remember {
        mutableStateOf<String?>(null)
    }

    val mainUiState = vm.mainUiState.collectAsStateWithLifecycle()


    LaunchedEffect(userId) {
        vm.checkIsAdmin()
        vm.loadBooks()
    }



    ModalNavigationDrawer(
        modifier = Modifier.fillMaxWidth(),
        drawerState = drawerState,
        drawerContent = {
            Column(modifier = Modifier.fillMaxWidth(0.7f)) {
                DrawerHeader(email)
                DrawerBody(
                    navController = navController,
                    selectedCategory,
                    mainUiState.value.isAdmin
                ) { category ->
                    selectedCategory = category
                }
            }
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                BottomMenu(
                    selectedScreen = selectedScreen,
                    onItemClick = { screen ->
                        selectedScreen = screen
                    }
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when {
                    mainUiState.value.isLoading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    mainUiState.value.error != null -> {
                        Text(
                            text = mainUiState.value.error ?: "Unknown error"
                        )
                    }

                    else -> {
                        when (selectedScreen) {
                            BottomScreen.Books -> {
                                val filteredBooks = if (selectedCategory == null || selectedCategory == "All") {
                                    mainUiState.value.books
                                } else {
                                    mainUiState.value.books.filter { book ->
                                        book.category == selectedCategory
                                    }
                                }

                                BooksContent(
                                    books = filteredBooks,
                                    onFavoriteClick = { book ->
                                        vm.onFavoriteClick(book)
                                    }
                                )
                            }

                            BottomScreen.Favorites -> {
                                FavoritesContent(
                                    books = mainUiState.value.books,
                                    onFavoriteClick = { book ->
                                        vm.onFavoriteClick(book)
                                    }
                                )
                            }

                            BottomScreen.Profile -> {
                                ProfileContent(
                                    email = email,
                                    userId = userId
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
