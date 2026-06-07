package com.example.bookstoreapp.ui.orders

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.models.Book
import com.example.domain.models.Order

@Composable
fun OrdersContent(
    orders: List<Order>,
    books: List<Book>,
    onBackClick: () -> Unit,
    onCancelOrderClick: (Order) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }

            Text(
                text = "My orders",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp, top = 8.dp)
            )
        }

        if (orders.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "You have no orders yet")
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp)
            ) {
                items(orders) { order ->
                    OrderItem(
                        order = order,
                        books = books,
                        onCancelClick = {
                            onCancelOrderClick(order)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun OrderItem(
    order: Order,
    books: List<Book>,
    onCancelClick: () -> Unit
) {
    val orderBooks = books.filter { book ->
        order.bookIds.contains(book.id)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Order #${order.id.take(6)}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Status: ${order.status}",
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            orderBooks.forEach { book ->
                Text(
                    text = "• ${book.title}",
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Total: ${order.totalPrice.toInt()} ₽",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )


            if (order.status != "canceled") {
                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = onCancelClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFB00020)
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Cancel order")
                }
            } else {
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Order canceled",
                    color = Color.Red,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}