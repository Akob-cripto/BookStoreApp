package com.example.bookstoreapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.bookstoreapp.ui.LoginScreen
import com.example.bookstoreapp.ui.main_screen.MainScreen
import com.example.bookstoreapp.ui.navigation.Login
import com.example.bookstoreapp.ui.navigation.Main

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContent {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = Login
            ) {
                composable<Login> {
                    LoginScreen(navController = navController)
                }

                composable<Main> {backStackEntry ->
                    val route = backStackEntry.toRoute<Main>()
                    MainScreen(email = route.email, userId = route.userId)
                }
            }
        }
    }
}