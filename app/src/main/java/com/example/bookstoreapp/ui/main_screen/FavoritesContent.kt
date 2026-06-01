package com.example.bookstoreapp.ui.main_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bookstoreapp.ui.main_screen.add_book_screen.BookItem
import com.example.domain.models.Book

@Composable
fun FavoritesContent(
    books: List<Book>,
    onFavoriteClick: (Book) -> Unit
) {
    val favoriteBooks = books.filter { it.isFavorite }

    if (favoriteBooks.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "No favorite books yet")
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 90.dp)
        ) {
            items(favoriteBooks) { book ->
                BookItem(
                    book = book,
                    onFavoriteClick = {
                        onFavoriteClick(book)
                    }
                )
            }
        }
    }
}