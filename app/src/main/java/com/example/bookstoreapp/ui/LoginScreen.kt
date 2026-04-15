package com.example.bookstoreapp.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.bookstoreapp.MainViewModel
import com.example.bookstoreapp.R
import com.example.bookstoreapp.ui.navigation.Main
import com.example.bookstoreapp.ui.theme.BoxFilterColor
import com.example.domain.validation.SignInResult
import com.example.domain.validation.SignUpResult
import org.koin.androidx.compose.koinViewModel


@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current

    val emailState = remember {
        mutableStateOf("akobeduardovic318@gmail.com")
    }

    val passwordState = remember {
        mutableStateOf("Akob2007)")
    }

    val vm: MainViewModel = koinViewModel()

    val signInResult by vm.signInResultLiveData.observeAsState()
    val signUpResult by vm.signUpResultLiveData.observeAsState()

    LaunchedEffect(signInResult) {
        when (val result = signInResult) {
            is SignInResult.Success -> {
                navController.navigate(Main(email = result.user.email, userId = result.user.userId))
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

    Image(
        painter = painterResource(R.drawable.bg_bookstore_login),
        contentDescription = "BG",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BoxFilterColor),
    )



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .size(200.dp)
                .clip(RoundedCornerShape(50.dp))
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(text = "Akob Book Store",
            color = Color.White,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif
        )

        Spacer(modifier = Modifier.height(10.dp))

        RoundedCornerTextField(
            text = emailState.value,
            label = "Email"
        ) {newText ->
            emailState.value = newText
        }


        Spacer(modifier = Modifier.height(10.dp))

        RoundedCornerTextField(
            text = passwordState.value,
            label = "Password"
        ) {newText ->
            passwordState.value = newText
        }


        LoginButton(text = "Sign In") {
                vm.signIn(email = emailState.value, password = passwordState.value)
        }

        LoginButton(text = "Sign Up") {
                vm.signUp(email = emailState.value, password = passwordState.value)
        }
    }
}