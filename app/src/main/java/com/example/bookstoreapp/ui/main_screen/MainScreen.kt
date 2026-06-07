package com.example.bookstoreapp.ui.main_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.example.bookstoreapp.ui.main_screen.MainViewModel
import com.example.bookstoreapp.ui.bottom_menu.BottomMenu
import com.example.bookstoreapp.ui.bottom_menu.BottomScreen
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.bookstoreapp.R
import com.example.bookstoreapp.navigation.Login
import com.example.bookstoreapp.ui.books.BooksContent
import com.example.bookstoreapp.ui.cart.CartContent
import com.example.bookstoreapp.ui.drawer.DrawerBody
import com.example.bookstoreapp.ui.drawer.DrawerHeader
import com.example.bookstoreapp.ui.favorites.FavoritesContent
import com.example.bookstoreapp.ui.orders.OrdersContent
import com.example.bookstoreapp.ui.profile.ProfileContent
import com.example.domain.models.Order
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    email: String,
    userId: String,
    navController: NavController,
    vm: MainViewModel
) {

    var searchQuery by remember {
        mutableStateOf("")
    }

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

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
        vm.loadCart()
    }


    ModalNavigationDrawer(
        modifier = Modifier.fillMaxWidth(),
        drawerState = drawerState,
        drawerContent = {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(280.dp)
                    .background(Color(0xFF102A38))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.bg_bookstore_login),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    alpha = 0.18f,
                    contentScale = ContentScale.Crop
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xCC102A38))
                )

                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    DrawerHeader(email)

                    DrawerBody(
                        navController = navController,
                        selectedCategory = selectedCategory,
                        isAdmin = mainUiState.value.isAdmin
                    ) { category ->
                        selectedCategory = category
                    }
                }
            }
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color(0xFFFAF7F2),
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
                            BottomScreen.Cart -> {
                                CartContent(
                                    books = mainUiState.value.books,
                                    cartBookIds = mainUiState.value.cartBookIds,
                                    onRemoveFromCartClick = { book ->
                                        vm.removeBookFromCart(book.id)
                                    },
                                    onCheckoutClick = {
                                        vm.checkout(email)
                                    }
                                )
                            }

                            BottomScreen.Books -> {
                                val filteredBooks =
                                    if (selectedCategory == null || selectedCategory == "All") {
                                        mainUiState.value.books
                                    } else {
                                        mainUiState.value.books.filter { book ->
                                            book.category.equals(
                                                selectedCategory,
                                                ignoreCase = true
                                            )
                                        }
                                    }

                                val searchedBooks = filteredBooks.filter { book ->
                                    val query = searchQuery.trim()

                                    query.isBlank() ||
                                            book.title.contains(query, ignoreCase = true) ||
                                            book.author.contains(query, ignoreCase = true)
                                }

                                BooksContent(
                                    books = searchedBooks,
                                    searchQuery = searchQuery,
                                    onSearchQueryChange = { newText ->
                                        searchQuery = newText
                                    },
                                    onMenuClick = {
                                        scope.launch {
                                            drawerState.open()
                                        }
                                    },
                                    onFavoriteClick = { book ->
                                        vm.onFavoriteClick(book)
                                    },
                                    navController = navController
                                )
                            }

                            BottomScreen.Favorites -> {
                                FavoritesContent(
                                    books = mainUiState.value.books,
                                    onFavoriteClick = { book ->
                                        vm.onFavoriteClick(book)
                                    },
                                    navController = navController
                                )
                            }

                            BottomScreen.Profile -> {
                                ProfileContent(
                                    email = email,
                                    onOrdersClick = {
                                        vm.loadMyOrders()
                                        selectedScreen = BottomScreen.Orders
                                    },
                                    onLogoutClick = {
                                        vm.signOut()

                                        navController.navigate(Login) {
                                            popUpTo(0) {
                                                inclusive = true
                                            }
                                        }
                                    }
                                )
                            }

                            BottomScreen.Orders -> {
                                OrdersContent(
                                    orders = mainUiState.value.orders,
                                    books = mainUiState.value.books,
                                    onBackClick = {
                                        selectedScreen = BottomScreen.Profile
                                    },
                                    onCancelOrderClick = { order: Order ->
                                        vm.cancelOrder(order.id)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}