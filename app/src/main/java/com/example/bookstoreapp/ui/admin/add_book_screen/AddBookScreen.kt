package com.example.bookstoreapp.ui.admin.add_book_screen

import android.net.Uri
import android.widget.Toast
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.bookstoreapp.ui.main_screen.MainViewModel
import com.example.bookstoreapp.R
import com.example.bookstoreapp.ui.components.CustomButton
import com.example.bookstoreapp.ui.components.RoundedCornerTextField
import com.example.bookstoreapp.ui.theme.BoxFilterColor
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import com.example.bookstoreapp.utils.ImageBase64Utils

@Composable
fun AddBookScreen(
    vm: MainViewModel,
    onBackClick: () -> Unit
) {


    val context = LocalContext.current

    val title = remember {
        mutableStateOf("")
    }

    val selectedCategory = remember {
        mutableStateOf("drama")
    }

    val description = remember {
        mutableStateOf("")
    }

    val price = remember {
        mutableStateOf("")
    }

    val author = remember {
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


    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AsyncImage(
            model = selectedImageUri.value,
            contentDescription = "BG",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BoxFilterColor)
        )

        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 40.dp, start = 16.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }

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
                text = author.value,
                label = "Author"
            ) { newText ->
                author.value = newText
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
                val titleValue = title.value.trim()
                val authorValue = author.value.trim()
                val descriptionValue = description.value.trim()
                val categoryValue = selectedCategory.value.trim()
                val priceValue = price.value.trim().toDoubleOrNull()
                val imageUriValue = selectedImageUri.value

                when {
                    titleValue.isBlank() -> {
                        Toast.makeText(context, "Введите название книги", Toast.LENGTH_SHORT).show()
                        return@CustomButton
                    }

                    descriptionValue.isBlank() -> {
                        Toast.makeText(context, "Введите описание книги", Toast.LENGTH_SHORT).show()
                        return@CustomButton
                    }

                    authorValue.isBlank() -> {
                        Toast.makeText(context, "Введите автора книги", Toast.LENGTH_SHORT).show()
                        return@CustomButton
                    }

                    categoryValue.isBlank() -> {
                        Toast.makeText(context, "Выберите категорию", Toast.LENGTH_SHORT).show()
                        return@CustomButton
                    }

                    priceValue == null -> {
                        Toast.makeText(context, "Введите корректную цену", Toast.LENGTH_SHORT)
                            .show()
                        return@CustomButton
                    }

                    priceValue <= 0.0 -> {
                        Toast.makeText(context, "Цена должна быть больше 0", Toast.LENGTH_SHORT)
                            .show()
                        return@CustomButton
                    }

                    imageUriValue == null -> {
                        Toast.makeText(context, "Выберите изображение книги", Toast.LENGTH_SHORT)
                            .show()
                        return@CustomButton
                    }
                }

                val imageBase64 = ImageBase64Utils.uriToBase64(
                    context = context,
                    uri = imageUriValue
                )

                if (imageBase64 == null) {
                    Toast.makeText(context, "Не удалось обработать изображение", Toast.LENGTH_SHORT).show()
                    return@CustomButton
                }

                vm.saveBook(
                    category = categoryValue,
                    imageUri = imageBase64,
                    title = titleValue,
                    description = descriptionValue,
                    author = authorValue,
                    price = priceValue
                )

                onBackClick()
            }
        }
    }
}