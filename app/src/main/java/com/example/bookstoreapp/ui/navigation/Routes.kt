package com.example.bookstoreapp.ui.navigation

import kotlinx.serialization.Serializable


@Serializable
data object Login

@Serializable
data class Main(
    val email: String,
    val userId: String
)

@Serializable
data class BookDetails(
    val bookId: String
)

@Serializable
object AddBook