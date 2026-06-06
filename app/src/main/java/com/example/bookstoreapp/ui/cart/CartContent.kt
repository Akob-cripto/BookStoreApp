package com.example.bookstoreapp.ui.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.models.Book

@Composable
fun CartContent(
    books: List<Book>,
    cartBookIds: List<String>,
    onRemoveFromCartClick: (Book) -> Unit,
    onCheckoutClick: () -> Unit
) {
    val cartBooks = books.filter { book ->
        cartBookIds.contains(book.id)
    }

    val totalPrice = cartBooks.sumOf { it.price }

    if (cartBooks.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Cart is empty",
                fontSize = 20.sp,
                color = Color.Gray
            )
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Your cart",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )

            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(cartBooks) { book ->
                    CartBookItem(
                        book = book,
                        onRemoveClick = {
                            onRemoveFromCartClick(book)
                        }
                    )
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Total",
                            fontSize = 18.sp,
                            color = Color.Gray
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Text(
                            text = "${totalPrice.toInt()} ₽",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1B3A4B)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = onCheckoutClick,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(text = "Checkout")
                    }
                }
            }
        }
    }
}

@Composable
fun CartBookItem(
    book: Book,
    onRemoveClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF3EDF7)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(width = 70.dp, height = 95.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF1B3A4B)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = book.title.take(1).uppercase(),
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = book.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = if (book.author.isBlank()) "Unknown author" else book.author,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "${book.price.toInt()} ₽",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1B3A4B)
                )
            }

            IconButton(onClick = onRemoveClick) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Remove from cart",
                    tint = Color.Red
                )
            }
        }
    }
}