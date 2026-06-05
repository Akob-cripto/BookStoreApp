package com.example.bookstoreapp.ui.main_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bookstoreapp.ui.main_screen.add_book_screen.BookItem
import com.example.bookstoreapp.ui.navigation.BookDetails
import com.example.domain.models.Book

@Composable
fun BooksContent(
    books: List<Book>,
    onFavoriteClick: (Book) -> Unit,
    navController: NavController
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Hello, reader 👋",
                fontSize = 16.sp,
                color = Color.Gray
            )

            Text(
                text = "Find your next book",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 90.dp)
        ) {
            items(books) { book ->
                BookItem(
                    book = book,
                    onFavoriteClick = {
                        onFavoriteClick(book)
                    },
                    onBookClick = {
                        navController.navigate(BookDetails(bookId = book.id))
                    }
                )
            }
        }
    }
}
