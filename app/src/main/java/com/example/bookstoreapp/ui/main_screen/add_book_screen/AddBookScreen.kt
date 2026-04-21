package com.example.bookstoreapp.ui.main_screen.add_book_screen

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import coil.compose.AsyncImage
import com.example.bookstoreapp.MainViewModel
import com.example.bookstoreapp.R
import com.example.bookstoreapp.ui.CustomButton
import com.example.bookstoreapp.ui.RoundedCornerTextField
import com.example.bookstoreapp.ui.theme.BoxFilterColor
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddBookScreen() {

    val vm = koinViewModel<MainViewModel>()

    val title = remember {
        mutableStateOf("")
    }

    val selectedCategory = remember{
        mutableStateOf("drama")
    }

    val description = remember {
        mutableStateOf("")
    }

    val price = remember {
        mutableStateOf("")
    }

    val selectedImageUri = remember {
        mutableStateOf<Uri?>(null)
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri: Uri? ->
            uri?.let {
                selectedImageUri.value = uri
            }
        })


    AsyncImage(
        model = selectedImageUri.value,
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
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .size(150.dp)
                .clip(RoundedCornerShape(50.dp))
        )


        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Add New Book",
            color = Color.White,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif
        )

        Spacer(modifier = Modifier.height(10.dp))

        RoundedCornerDropDownMenu { selectedItem ->
            selectedCategory.value = selectedItem
        }

        Spacer(modifier = Modifier.height(10.dp))

        RoundedCornerTextField(
            text = title.value,
            label = "Title"
        ) { newText ->
            title.value = newText
        }


        Spacer(modifier = Modifier.height(10.dp))

        RoundedCornerTextField(
            text = description.value,
            label = "Description"
        ) { newText ->
            description.value = newText
        }

        Spacer(modifier = Modifier.height(10.dp))

        RoundedCornerTextField(
            text = price.value,
            label = "Price"
        ) { newText ->
            price.value = newText
        }


        CustomButton(text = "Select Image") {
            launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        CustomButton(text = "Save") {
            vm.saveBook(
                category = selectedCategory.value,
                imageUri = selectedImageUri.value.toString(),
                title = title.value,
                description = description.value
            )
        }
    }
}