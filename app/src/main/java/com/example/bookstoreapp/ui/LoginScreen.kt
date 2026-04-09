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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.bookstoreapp.MainViewModel
import com.example.bookstoreapp.R
import com.example.bookstoreapp.ui.main_screen.MainScreen
import com.example.bookstoreapp.ui.theme.BoxFilterColor
import com.example.domain.validation.SignInResult
import com.example.domain.validation.SignUpResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


@Composable
fun LoginScreen(navController: NavController) {




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
        Log.e("MyLog", "signInResult: $signInResult")
        when (val result = signInResult) {
            SignInResult.Success -> {
                navController.navigate("main")
            }
            else -> {
                Log.e("MyLog", "signInResult: $signInResult")
            }
        }
    }

    LaunchedEffect(signUpResult) {
        Log.e("MyLog", "signUpResult: $signInResult")
        when (val result = signUpResult) {
            SignUpResult.Success -> {
                navController.navigate("main")
            }
            else -> {
                Log.e("MyLog", "signUpResult: $signUpResult")

            }
        }
    }



    val coroutineScope = rememberCoroutineScope()

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
            .padding(start = 40.dp, end = 40.dp),
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
            coroutineScope.launch{
                vm.get(email = emailState.value, password = passwordState.value)
            }
        }


        LoginButton(text = "Sign Up") {
            coroutineScope.launch{
                vm.save(email = emailState.value, password = passwordState.value)
            }
        }

    }
}