package com.example.bookstoreapp.ui.auth


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bookstoreapp.ui.main_screen.MainViewModel
import com.example.bookstoreapp.R
import com.example.bookstoreapp.navigation.Login
import com.example.bookstoreapp.navigation.Main
import com.example.bookstoreapp.ui.components.CustomButton
import com.example.bookstoreapp.ui.components.RoundedCornerTextField
import com.example.domain.validation.SignInResult
import com.example.domain.validation.SignUpResult
import org.koin.androidx.compose.koinViewModel


@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current

    val emailState = remember {
        mutableStateOf("")
    }

    val passwordState = remember {
        mutableStateOf("")
    }

    val vm: MainViewModel = koinViewModel()

    val signInResult by vm.signInResultLiveData.observeAsState()
    val signUpResult by vm.signUpResultLiveData.observeAsState()

    LaunchedEffect(signInResult) {
        when (val result = signInResult) {
            is SignInResult.Success -> {
                navController.navigate(
                    Main(
                        email = result.user.email,
                        userId = result.user.userId
                    )
                ) {
                    popUpTo(Login) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }

                vm.clearSignInResult()
            }

            is SignInResult.ValidationError -> {
                val error = result.error
                showAuthValidationError(error, context)
                vm.clearSignInResult()
            }

            is  SignInResult.ServerError -> {
                Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                vm.clearSignInResult()
            }

            else -> Unit
        }
    }

    LaunchedEffect(signUpResult) {
        when(val result = signUpResult) {
            is SignUpResult.Success -> {
                navController.navigate(Main(email = result.user.email, userId = result.user.userId))
                vm.clearSignUpResult()
                vm.checkIsAdmin()
            }

            is SignUpResult.ValidationError -> {
                showAuthValidationError(result.error, context)
                vm.clearSignUpResult()
            }

            is SignUpResult.ServerError -> {
                Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                vm.clearSignUpResult()
            }

            else -> Unit
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.bg_bookstore_login),
            contentDescription = "BG",
            modifier = Modifier.fillMaxSize().background(Color.White, RoundedCornerShape(32.dp)),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xCC0B2230))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(32.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFDCCFC1).copy(alpha = 0.96f)
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(R.drawable.logo),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(RoundedCornerShape(34.dp))
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Akob Book Store",
                        color = Color(0xFF1B3A4B),
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Welcome back, reader",
                        color = Color.Gray,
                        fontSize = 15.sp
                    )

                    Spacer(modifier = Modifier.height(22.dp))

                    RoundedCornerTextField(
                        text = emailState.value,
                        label = "Email"
                    ) { newText ->
                        emailState.value = newText
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    RoundedCornerTextField(
                        text = passwordState.value,
                        label = "Password",
                        isPassword = true
                    ) { newText ->
                        passwordState.value = newText
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    CustomButton(
                        text = "Sign In",
                        modifier = Modifier.fillMaxWidth(),
                        containerColor = Color(0xFF1B3A4B)
                    ) {
                        vm.signIn(
                            email = emailState.value,
                            password = passwordState.value
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    androidx.compose.material3.OutlinedButton(
                        onClick = {
                            vm.signUp(
                                email = emailState.value,
                                password = passwordState.value
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        colors = androidx.compose.material3.ButtonDefaults.outlinedButtonColors(
                            contentColor = Color(0xFF1B3A4B)
                        )
                    ) {
                        Text(text = "Create account")
                    }
                }
            }
        }
    }
}