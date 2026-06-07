package com.example.bookstoreapp.ui.drawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bookstoreapp.R
import com.example.bookstoreapp.navigation.AddBook
import com.example.bookstoreapp.ui.theme.DarkBlue
import com.example.bookstoreapp.ui.theme.DarkTransparentBlue
import com.example.bookstoreapp.ui.theme.GrayLight

@Composable
fun DrawerBody(
    navController: NavController,
    selectedCategory: String?,
    isAdmin: Boolean,
    onCategoryClick: (String?) -> Unit
) {
    val categoriesList = remember {
        listOf("All", "Favorite", "Fantasy", "Drama", "Bestsellers")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 18.dp)
    ) {
        Text(
            text = "Categories",
            color = Color.White.copy(alpha = 0.75f),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        categoriesList.forEach { item ->
            val isSelected =
                selectedCategory == item || (selectedCategory == null && item == "All")

            CategoryDrawerItem(
                title = item,
                isSelected = isSelected,
                onClick = {
                    onCategoryClick(
                        if (item == "All") null else item
                    )
                }
            )

            Spacer(modifier = Modifier.height(10.dp))
        }

        Spacer(modifier = Modifier.weight(1f))

        if (isAdmin) {
            Button(
                onClick = {
                    navController.navigate(AddBook)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 28.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFDF7EF),
                    contentColor = Color(0xFF1B3A4B)
                )
            ) {
                Text(
                    text = "Admin panel",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


@Composable
fun CategoryDrawerItem(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) {
        Color(0xFFFDF7EF)
    } else {
        Color.White.copy(alpha = 0.08f)
    }

    val textColor = if (isSelected) {
        Color(0xFF1B3A4B)
    } else {
        Color.White
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(backgroundColor)
            .clickable {
                onClick()
            }
            .padding(vertical = 14.dp, horizontal = 18.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = title,
            color = textColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}