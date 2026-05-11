package com.example.bookstoreapp.ui.main_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.bookstoreapp.MainViewModel
import com.example.bookstoreapp.ui.main_screen.add_book_screen.AddBookScreen
import com.example.bookstoreapp.ui.main_screen.buttiom_menu.BottomMenu
import com.example.bookstoreapp.ui.theme.DarkBlue


@Composable
fun MainScreen(
    email: String,
    userId: String,
    navController: NavController,
    vm: MainViewModel
) {
    val drawerState = rememberDrawerState(DrawerValue.Open)

    val mainUiState = vm.mainUiState.collectAsStateWithLifecycle()

    ModalNavigationDrawer(
        modifier = Modifier.fillMaxWidth(),
        drawerState = drawerState,
        drawerContent = {
            Column(modifier = Modifier.fillMaxWidth(0.7f)) {
                DrawerHeader(email)
                DrawerBody(navController = navController)
            }
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = { BottomMenu() }) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = paddingValues)
            ) {
                when {
                    mainUiState.value.isLoading -> {
                        CircularProgressIndicator()
                    }

                    mainUiState.value.error != null -> {
                        Text(
                            text = mainUiState.value.error ?: "Unknown error"
                        )
                    }

                    else -> {
                        LazyColumn {
                            items(mainUiState.value.books) { book ->
                                Text(text = book.title)
                            }
                        }
                    }
                }
            }
        }
    }
}