package com.example.bookstoreapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.bookstoreapp.ui.auth.LoginScreen
import com.example.bookstoreapp.ui.main_screen.MainScreen
import com.example.bookstoreapp.ui.admin.add_book_screen.AddBookScreen
import com.example.bookstoreapp.ui.books.book_details_screen.BookDetailsScreen
import com.example.bookstoreapp.navigation.AddBook
import com.example.bookstoreapp.navigation.BookDetails
import com.example.bookstoreapp.navigation.Login
import com.example.bookstoreapp.navigation.Main
import com.example.bookstoreapp.ui.main_screen.MainViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContent {
            val navController = rememberNavController()
            val vm: MainViewModel = koinViewModel()
            NavHost(
                navController = navController,
                startDestination = Login
            ) {
                composable<Login> {
                    LoginScreen(navController = navController)
                }

                composable<Main> {backStackEntry ->
                    val route = backStackEntry.toRoute<Main>()
                    MainScreen(
                        email = route.email,
                        userId = route.userId,
                        navController = navController,
                        vm = vm
                    )
                }

                composable<AddBook>() {
                    AddBookScreen(
                        vm,
                        onBackClick = { navController.popBackStack() }
                    )
                }

                composable<BookDetails> { backStackEntry ->
                    val route = backStackEntry.toRoute<BookDetails>()

                    BookDetailsScreen(
                        bookId = route.bookId,
                        navController = navController,
                        vm = vm
                    )
                }
            }
        }
    }
}